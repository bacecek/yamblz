package com.bacecek.yamblz.data.repository.weather;

import android.content.Context;
import android.content.SharedPreferences;

import com.bacecek.yamblz.R;
import com.bacecek.yamblz.data.network.WeatherApi;
import com.bacecek.yamblz.data.network.response.WeatherResponse;
import com.bacecek.yamblz.util.AppResources;
import com.bacecek.yamblz.util.Consts;
import com.bacecek.yamblz.util.Utils;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.gson.Gson;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Denis Buzmakov on 13.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class WeatherRepositoryImpl implements WeatherRepository {

    private Context mContext;
    private WeatherApi mApi;
    private RxSharedPreferences mRxPreferences;
    private SharedPreferences mPreferences;
    private AppResources mResources;

    public WeatherRepositoryImpl(Context context, WeatherApi api, RxSharedPreferences rxPreferences, SharedPreferences preferences, AppResources resources) {
        mContext = context.getApplicationContext();
        mApi = api;
        mRxPreferences = rxPreferences;
        mPreferences = preferences;
        mResources = resources;
    }

    @Override
    public Single<WeatherResponse> getCurrentWeather(String city) {
        if(Utils.isOnline(mContext)) {
            Timber.d("get weather from api");
            return mApi.getCurrentWeather(city, mPreferences.getString(Consts.Prefs.KEY_UNITS, mResources.getString(R.string.metric)))
                    .subscribeOn(Schedulers.io());
        } else {
            Timber.d("get weather from local storage");
            return Single.fromObservable(mRxPreferences.getString(Consts.Prefs.KEY_LAST_WEATHER, "")
                    .asObservable())
                    .map(s -> new Gson().fromJson(s, WeatherResponse.class));
        }
    }

    @Override
    public void saveLastWeather(WeatherResponse weather) {
        mPreferences.edit()
                .putString(Consts.Prefs.KEY_LAST_WEATHER, new Gson().toJson(weather, WeatherResponse.class))
                .apply();
    }
}
