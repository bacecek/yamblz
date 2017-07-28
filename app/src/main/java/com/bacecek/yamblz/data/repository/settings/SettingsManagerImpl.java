package com.bacecek.yamblz.data.repository.settings;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bacecek.yamblz.R;
import com.bacecek.yamblz.data.presentation.CityCoords;
import com.bacecek.yamblz.util.AppResources;
import com.bacecek.yamblz.util.Consts;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import io.reactivex.Observable;

/**
 * Created by Denis Buzmakov on 13.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class SettingsManagerImpl implements SettingsManager {
    private SharedPreferences preferences;
    private RxSharedPreferences rxPreferences;
    private AppResources resources;

    public SettingsManagerImpl(SharedPreferences preferences, RxSharedPreferences rxPreferences, AppResources resources) {
        this.preferences = preferences;
        this.rxPreferences = rxPreferences;
        this.resources = resources;
    }

    @Override
    public String getTemperatureUnits() {
        return preferences.getString(Consts.Prefs.KEY_TEMP_UNITS, resources.getString(R.string.metric));
    }

    @Override
    public void saveTemperatureUnits(String units) {
        preferences.edit()
                .putString(Consts.Prefs.KEY_TEMP_UNITS, units)
                .apply();
    }

    @Override
    public Observable<String> getTemperatureUnitsObservable() {
        return rxPreferences.getString(Consts.Prefs.KEY_TEMP_UNITS, resources.getString(R.string.metric))
                .asObservable();
    }

    @Override
    public int getUpdateInterval() {
        return preferences.getInt(Consts.Prefs.KEY_UPDATE_INTERVAL, Consts.DEFAULT_UPDATE_INTERVAL);
    }

    @Override
    public void saveUpdateInterval(int interval) {
        preferences.edit()
                .putInt(Consts.Prefs.KEY_UPDATE_INTERVAL, interval)
                .apply();
    }

    @Override
    public Observable<Integer> getUpdateIntervalObservable() {
        return rxPreferences.getInteger(Consts.Prefs.KEY_UPDATE_INTERVAL, Consts.DEFAULT_UPDATE_INTERVAL)
                .asObservable();
    }

    @Nullable
    @Override
    public String getCityId() {
        return preferences.getString(Consts.Prefs.KEY_CITY_ID, null);
    }

    @Override
    public void saveCityId(@NonNull String cityId) {
        preferences.edit()
                .putString(Consts.Prefs.KEY_CITY_ID, cityId)
                .apply();
    }

    @NonNull
    @Override
    public Observable<String> getCityIdObservable() {
        return rxPreferences.getString(Consts.Prefs.KEY_CITY_ID)
                .asObservable();
    }

    @Nullable
    @Override
    public String getCityName() {
        return preferences.getString(Consts.Prefs.KEY_CITY_NAME, null);
    }

    @Override
    public void saveCityName(@NonNull String cityName) {
        preferences.edit()
                .putString(Consts.Prefs.KEY_CITY_NAME, cityName)
                .apply();
    }

    @NonNull
    @Override
    public Observable<String> getCityNameObservable() {
        return rxPreferences.getString(Consts.Prefs.KEY_CITY_NAME)
                .asObservable();
    }

    @Override
    public CityCoords getCityCoords() {
        float longitude = preferences.getFloat(Consts.Prefs.KEY_CITY_LON, Float.MIN_VALUE);
        float latitude = preferences.getFloat(Consts.Prefs.KEY_CITY_LAT, Float.MIN_VALUE);
        if (longitude == Float.MIN_VALUE || latitude == Float.MIN_VALUE) {
            return null;
        } else {
            return new CityCoords.Builder()
                    .setLongitude(longitude)
                    .setLatitude(latitude)
                    .build();
        }
    }

    @Override
    public void saveCityCoords(@NonNull CityCoords cityCoords) {
        preferences.edit()
                .putFloat(Consts.Prefs.KEY_CITY_LON, cityCoords.getLongitude())
                .putFloat(Consts.Prefs.KEY_CITY_LAT, cityCoords.getLatitude())
                .apply();
    }

    @NonNull
    @Override
    public Observable<CityCoords> getCityCoordsObservable() {
        Observable<Float> longitude = rxPreferences.getFloat(Consts.Prefs.KEY_CITY_LON)
                .asObservable();
        Observable<Float> latitude = rxPreferences.getFloat(Consts.Prefs.KEY_CITY_LAT)
                .asObservable();

        return Observable.just(new CityCoords.Builder())
                .zipWith(longitude, CityCoords.Builder::setLongitude)
                .zipWith(latitude, CityCoords.Builder::setLatitude)
                .map(CityCoords.Builder::build);
    }
}
