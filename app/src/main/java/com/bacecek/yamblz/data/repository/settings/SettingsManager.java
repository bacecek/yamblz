package com.bacecek.yamblz.data.repository.settings;

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
}
