package com.bacecek.yamblz.data.repository.cache;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.bacecek.yamblz.data.network.response.WeatherResponse;
import com.bacecek.yamblz.util.Consts;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.gson.Gson;

import io.reactivex.Observable;

/**
 * Created by vandrikeev on 30.07.17.
 */
public class WeatherCacheImpl implements WeatherCache {

    private SharedPreferences preferences;

    private RxSharedPreferences rxPreferences;

    public WeatherCacheImpl(SharedPreferences preferences, RxSharedPreferences rxPreferences) {
        this.preferences = preferences;
        this.rxPreferences = rxPreferences;
    }

    @Override
    public void cacheWeather(@NonNull WeatherResponse weather) {
        preferences.edit()
                .putString(Consts.Prefs.KEY_LAST_WEATHER, new Gson().toJson(weather, WeatherResponse.class))
                .apply();
    }

    @Override
    public Observable<WeatherResponse> loadCache() {
        return rxPreferences.getString(Consts.Prefs.KEY_LAST_WEATHER, "")
                .asObservable()
                .map(s -> new Gson().fromJson(s, WeatherResponse.class));
    }
}
