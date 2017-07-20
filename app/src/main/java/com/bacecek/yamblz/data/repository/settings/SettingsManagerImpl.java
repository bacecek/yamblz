package com.bacecek.yamblz.data.repository.settings;

import android.content.SharedPreferences;

import com.bacecek.yamblz.R;
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
}
