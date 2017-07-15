package com.bacecek.yamblz.data.network;

import com.bacecek.yamblz.data.network.response.WeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Denis Buzmakov on 12.07.2017.
 * <buzmakov.da@gmail.com>
 */

public interface WeatherApi {
    @GET("weather")
    Observable<WeatherResponse> getCurrentWeather(@Query("q") String city);
}
