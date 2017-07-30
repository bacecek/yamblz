package com.bacecek.yamblz.data.repository.places;

import com.bacecek.yamblz.AbstractTestWithServicesAndResources;
import com.bacecek.yamblz.data.network.PlacesApiError;
import com.bacecek.yamblz.data.network.places.PlacesResponseStatus;
import com.bacecek.yamblz.data.presentation.City;
import com.bacecek.yamblz.data.presentation.CityCoords;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

public class PlacesRepositoryImplTest extends AbstractTestWithServicesAndResources {

    private PlacesRepositoryImpl placesRepository;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        placesRepository = new PlacesRepositoryImpl(placesApi, settingsManager);
    }

    @Test
    public void getSuggestions_OK() throws Exception {

        City[] suggestions = new City[]{
                new City("Moscow, Russia", "ChIJybDUc_xKtUYRTM9XV8zWRD0"),
                new City("Moscow, ID, United States", "ChIJ0WHAIi0hoFQRbK3q5g0V_T4"),
                new City("Moscow Mills, MO, United States", "ChIJA280k5i_3ocRe-IAInh_ilY"),
                new City("Moscow, PA, United States", "ChIJDaDKkrXCxIkR16VXi1WamHU")
        };

        Mockito.when(placesApi.suggestions(MOSCOW_CITY_NAME)).thenReturn(Single.just(getSuggestionsMoscowOK()));
        TestObserver<List<City>> observer = new TestObserver<>();
        placesRepository.getSuggestions(MOSCOW_CITY_NAME).subscribe(observer);

        observer.assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .assertValue(cities -> {
                    Assert.assertArrayEquals(suggestions, cities.toArray());
                    return true;
                });
    }

    @Test
    public void getSuggestions_NetworkError() throws Exception {
        Mockito.when(placesApi.suggestions(MOSCOW_CITY_NAME)).thenReturn(Single.error(networkError));
        TestObserver<List<City>> observer = new TestObserver<>();
        placesRepository.getSuggestions(MOSCOW_CITY_NAME).subscribe(observer);

        observer.assertSubscribed()
                .assertNotComplete()
                .assertError(networkError);
    }

    @Test
    public void getSuggestions_ApiError() throws Exception {
        Mockito.when(placesApi.suggestions(MOSCOW_CITY_NAME)).thenReturn(Single.just(getSuggestionsMoscowNoApiKey()));
        TestObserver<List<City>> observer = new TestObserver<>();
        placesRepository.getSuggestions(MOSCOW_CITY_NAME).subscribe(observer);

        observer.assertSubscribed()
                .assertNotComplete()
                .assertError(error -> (error instanceof PlacesApiError) &&
                        ((PlacesApiError) error).getStatus() == PlacesResponseStatus.REQUEST_DENIED);
    }

    @Test
    public void getPlace_OK() throws Exception {
        Mockito.when(placesApi.place(MOSCOW_PLACE_ID)).thenReturn(Single.just((getPlaceMoscowOK())));
        placesRepository.loadAndSaveCoordsForCity(MOSCOW_PLACE_ID);

        Mockito.verify(settingsManager, Mockito.times(1)).saveCityName(MOSCOW_CITY_NAME);

        ArgumentCaptor<CityCoords> argument = ArgumentCaptor.forClass(CityCoords.class);
        Mockito.verify(settingsManager, Mockito.times(1)).saveCityCoords(argument.capture());
        Assert.assertEquals(moscowCoords, argument.getValue());
    }

    @Test
    public void getPlace_NetworkError() throws Exception {
        Mockito.when(placesApi.place(MOSCOW_PLACE_ID)).thenReturn(Single.error(networkError));
        placesRepository.loadAndSaveCoordsForCity(MOSCOW_PLACE_ID);
        Mockito.verifyZeroInteractions(settingsManager);
    }

    @Test
    public void getPlace_ApiError() throws Exception {
        Mockito.when(placesApi.place(MOSCOW_PLACE_ID)).thenReturn(Single.just(getPlaceMoscowNoApiKey()));
        placesRepository.loadAndSaveCoordsForCity(MOSCOW_PLACE_ID);
        Mockito.verifyZeroInteractions(settingsManager);
    }
}
