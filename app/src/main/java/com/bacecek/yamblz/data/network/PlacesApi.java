package com.bacecek.yamblz.data.network;

import com.bacecek.yamblz.data.network.places.PlaceResponse;
import com.bacecek.yamblz.data.network.places.SuggestionsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vandrikeev on 22.07.17.
 */

public interface PlacesApi {

    @GET("autocomplete/json?types=(cities)")
    Single<SuggestionsResponse> suggestions(@Query("input") String query);

    @GET("details/json")
    Single<PlaceResponse> place(@Query("placeid") String placeId);
}
