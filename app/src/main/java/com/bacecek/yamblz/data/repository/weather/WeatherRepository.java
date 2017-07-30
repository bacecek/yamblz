package com.bacecek.yamblz.data.repository.weather;

import com.bacecek.yamblz.data.network.response.WeatherResponse;

import io.reactivex.Observable;

/**
 * Created by Denis Buzmakov on 13.07.2017.
 * <buzmakov.da@gmail.com>
 */

public interface WeatherRepository {
    Observable<WeatherResponse> getCurrentWeather();
    void saveLastWeather(WeatherResponse weather);
}
