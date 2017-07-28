package com.bacecek.yamblz.data.repository.weather;

import android.content.SharedPreferences;

import com.bacecek.yamblz.data.network.WeatherApi;
import com.bacecek.yamblz.data.network.response.WeatherResponse;
import com.bacecek.yamblz.data.repository.settings.SettingsManager;
import com.bacecek.yamblz.util.AppResources;
import com.bacecek.yamblz.util.Consts;
import com.bacecek.yamblz.util.Utils;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.gson.Gson;

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
    private RxSharedPreferences rxPreferences;
    private SharedPreferences preferences;
    private AppResources resources;
    private SettingsManager settingsManager;

    public WeatherRepositoryImpl(Utils utils,
                                 WeatherApi api,
                                 RxSharedPreferences rxPreferences,
                                 SharedPreferences preferences,
                                 AppResources resources,
                                 SettingsManager settingsManager) {
        this.utils = utils;
        this.weatherApi = api;
        this.rxPreferences = rxPreferences;
        this.preferences = preferences;
        this.resources = resources;
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
            return rxPreferences.getString(Consts.Prefs.KEY_LAST_WEATHER, "")
                    .asObservable()
                    .map(s -> new Gson().fromJson(s, WeatherResponse.class));
        }
    }

    @Override
    public Observable<WeatherResponse> getCurrentWeather(String city) {
        if (utils.isOnline()) {
            Timber.d("get weather from api");
            return weatherApi.getCurrentWeather(city)
                    .subscribeOn(Schedulers.io());
        } else {
            Timber.d("get weather from local storage");
            return rxPreferences.getString(Consts.Prefs.KEY_LAST_WEATHER, "")
                    .asObservable()
                    .map(s -> new Gson().fromJson(s, WeatherResponse.class));
        }
    }

    @Override
    public void saveLastWeather(WeatherResponse weather) {
        preferences.edit()
                .putString(Consts.Prefs.KEY_LAST_WEATHER, new Gson().toJson(weather, WeatherResponse.class))
                .apply();
    }
}
