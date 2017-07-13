package com.bacecek.yamblz.data.repository.weather;

import com.bacecek.yamblz.data.network.response.WeatherResponse;

import io.reactivex.Single;

/**
 * Created by Denis Buzmakov on 13.07.2017.
 * <buzmakov.da@gmail.com>
 */

public interface WeatherRepository {
    Single<WeatherResponse> getCurrentWeather(String city);
    void saveLastWeather(WeatherResponse weather);
}
