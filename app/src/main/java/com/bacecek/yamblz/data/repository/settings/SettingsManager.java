package com.bacecek.yamblz.data.repository.settings;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bacecek.yamblz.data.presentation.CityCoords;

import io.reactivex.Observable;

/**
 * Created by Denis Buzmakov on 13.07.2017.
 * <buzmakov.da@gmail.com>
 */

public interface SettingsManager {
    String getTemperatureUnits();

    void saveTemperatureUnits(String units);

    Observable<String> getTemperatureUnitsObservable();

    int getUpdateInterval();

    void saveUpdateInterval(int interval);

    Observable<Integer> getUpdateIntervalObservable();

    @Nullable
    String getCityId();

    void saveCityId(@NonNull String cityId);

    @NonNull
    Observable<String> getCityIdObservable();

    @Nullable
    String getCityName();

    void saveCityName(@NonNull String cityName);

    @NonNull
    Observable<String> getCityNameObservable();

    @Nullable
    CityCoords getCityCoords();

    void saveCityCoords(@NonNull CityCoords cityCoords);

    @NonNull
    Observable<CityCoords> getCityCoordsObservable();
}
