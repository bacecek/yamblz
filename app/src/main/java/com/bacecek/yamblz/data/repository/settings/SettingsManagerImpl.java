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

    private SharedPreferences mPreferences;
    private RxSharedPreferences mRxPreferences;
    private AppResources mResources;

    public SettingsManagerImpl(SharedPreferences preferences, RxSharedPreferences rxPreferences, AppResources resources) {
        mPreferences = preferences;
        mRxPreferences = rxPreferences;
        mResources = resources;
    }

    @Override
    public String getTemperatureUnits() {
        return mPreferences.getString(Consts.Prefs.KEY_UNITS, mResources.getString(R.string.metric));
    }

    @Override
    public void saveTemperatureUnits(String units) {
        mPreferences.edit()
                .putString(Consts.Prefs.KEY_UNITS, units)
                .apply();
    }

    @Override
    public Observable<String> getTemperatureUnitsObservable() {
        return mRxPreferences.getString(Consts.Prefs.KEY_UNITS, mResources.getString(R.string.metric))
                .asObservable();
    }

    @Override
    public int getUpdateInterval() {
        return mPreferences.getInt(Consts.Prefs.KEY_UPDATE_INTERVAL, Consts.DEFAULT_UPDATE_INTERVAL);
    }

    @Override
    public void saveUpdateInterval(int interval) {
        mPreferences.edit()
                .putInt(Consts.Prefs.KEY_UPDATE_INTERVAL, interval)
                .apply();
    }

    @Override
    public Observable<Integer> getUpdateIntervalObservable() {
        return mRxPreferences.getInteger(Consts.Prefs.KEY_UPDATE_INTERVAL, Consts.DEFAULT_UPDATE_INTERVAL)
                .asObservable();
    }
}
