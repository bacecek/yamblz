package com.bacecek.yamblz.data.repository.places;

import com.bacecek.yamblz.data.presentation.City;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by vandrikeev on 25.07.17.
 */

public interface PlacesRepository {

    Single<List<City>> getSuggestions(String query);

    void loadAndSaveCoordsForCity(String placeId);
}
