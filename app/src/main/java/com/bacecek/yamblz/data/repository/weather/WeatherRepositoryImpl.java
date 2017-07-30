package com.bacecek.yamblz.data.repository.weather;

import com.bacecek.yamblz.data.network.WeatherApi;
import com.bacecek.yamblz.data.network.response.WeatherResponse;
import com.bacecek.yamblz.data.repository.cache.WeatherCache;
import com.bacecek.yamblz.data.repository.settings.SettingsManager;
import com.bacecek.yamblz.util.Utils;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Denis Buzmakov on 13.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class WeatherRepositoryImpl implements WeatherRepository {

    private Utils utils;
    private WeatherApi weatherApi;
    private WeatherCache weatherCache;
    private SettingsManager settingsManager;

    public WeatherRepositoryImpl(Utils utils,
                                 WeatherApi api,
                                 WeatherCache weatherCache,
                                 SettingsManager settingsManager) {
        this.utils = utils;
        this.weatherApi = api;
        this.weatherCache = weatherCache;
        this.settingsManager = settingsManager;
    }

    @Override
    public Observable<WeatherResponse> getCurrentWeather() {
        if (utils.isOnline()) {
            Timber.d("get weather from api");
            return settingsManager.getCityCoordsObservable()
                    .flatMapSingle(cityCoords ->
                            weatherApi.getCurrentWeather(cityCoords.getLongitude(), cityCoords.getLatitude()))
                    .subscribeOn(Schedulers.io());
        } else {
            Timber.d("get weather from local storage");
            return weatherCache.loadCache();
        }
    }

    @Override
    public void saveLastWeather(WeatherResponse weather) {
        weatherCache.cacheWeather(weather);
    }
}
