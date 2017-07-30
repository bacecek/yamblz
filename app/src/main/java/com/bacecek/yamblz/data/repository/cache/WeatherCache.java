package com.bacecek.yamblz.data.repository.cache;

import android.support.annotation.NonNull;

import com.bacecek.yamblz.data.network.response.WeatherResponse;

import io.reactivex.Observable;

/**
 * Created by vandrikeev on 30.07.17.
 */

public interface WeatherCache {

    void cacheWeather(@NonNull WeatherResponse weather);

    Observable<WeatherResponse> loadCache();
}
