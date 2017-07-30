package com.bacecek.yamblz;

import com.bacecek.yamblz.data.network.places.PlaceResponse;
import com.bacecek.yamblz.data.network.places.SuggestionsResponse;
import com.bacecek.yamblz.data.network.response.WeatherResponse;
import com.bacecek.yamblz.data.presentation.CityCoords;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractTestWithResources {

    private static ResourcesReader resourcesReader = new ResourcesReader();

    protected static Throwable networkError = new SocketTimeoutException();

    protected static CityCoords moscowCoords = new CityCoords.Builder()
            .setLatitude(55.755826f)
            .setLongitude(37.6173f)
            .build();

    protected static String MOSCOW_CITY_NAME = "Moscow";
    protected static String MOSCOW_PLACE_ID = "ChIJybDUc_xKtUYRTM9XV8zWRD0";

    protected SuggestionsResponse getSuggestionsMoscowOK() throws FileNotFoundException {
        return resourcesReader.getSuggestionsMoscowOK();
    }

    protected SuggestionsResponse getSuggestionsMoscowNoApiKey() throws FileNotFoundException {
        return resourcesReader.getSuggestionsMoscowNoApiKey();
    }

    protected PlaceResponse getPlaceMoscowOK() throws FileNotFoundException {
        return resourcesReader.getDetailsMoscowOK();
    }

    protected PlaceResponse getPlaceMoscowNoApiKey() throws FileNotFoundException {
        return resourcesReader.getDetailsMoscowNoApiKey();
    }

    protected WeatherResponse getWeatherMoscowOK() throws FileNotFoundException {
        return resourcesReader.getWeatherMoscowOK();
    }
}
