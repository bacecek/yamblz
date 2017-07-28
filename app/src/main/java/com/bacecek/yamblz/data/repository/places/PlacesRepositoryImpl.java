package com.bacecek.yamblz.data.repository.places;

import android.support.annotation.NonNull;

import com.bacecek.yamblz.data.network.PlacesApi;
import com.bacecek.yamblz.data.network.places.Suggestion;
import com.bacecek.yamblz.data.presentation.City;
import com.bacecek.yamblz.data.presentation.CityCoords;
import com.bacecek.yamblz.data.repository.settings.SettingsManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vandrikeev on 25.07.17.
 */
@Singleton
public class PlacesRepositoryImpl implements PlacesRepository {

    private PlacesApi api;
    private SettingsManager settings;

    @Inject
    public PlacesRepositoryImpl(@NonNull @Named("places") PlacesApi api,
                                @NonNull SettingsManager settings) {
        this.api = api;
        this.settings = settings;
    }

    @Override
    public Single<List<City>> getSuggestions(String query) {
        return api.suggestions(query)
                .subscribeOn(Schedulers.io())
                .map((it) -> {
                    List<Suggestion> suggestions = it.getSuggestions();
                    List<City> cities = new ArrayList<>();
                    for (Suggestion suggestion : suggestions) {
                        cities.add(new City(suggestion.getDescription(), suggestion.getPlaceId()));
                    }
                    return cities;
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void loadAndSaveCoordsForCity(String placeId) {
        api.place(placeId)
                .subscribeOn(Schedulers.io())
                .doOnSuccess((response) -> {
                    if (response.getStatus().equals("OK")) {
                        settings.saveCityName(response.getResult().getName());
                    } else {
                        throw new RuntimeException(String.format("Bad response from API: %s", response.getStatus()));
                    }
                })
                .map((it) -> new CityCoords.Builder()
                        .setLongitude(it.getResult().getGeometry().getCoordinates().getLongitude())
                        .setLatitude(it.getResult().getGeometry().getCoordinates().getLatitude())
                        .build())
                .subscribe(cityCoords -> settings.saveCityCoords(cityCoords));
    }
}
