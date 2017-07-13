package com.bacecek.yamblz.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.bacecek.yamblz.App;
import com.bacecek.yamblz.data.repository.settings.SettingsManager;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Denis Buzmakov on 13.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class SettingsViewModel extends ViewModel {

    public SettingsViewModel() {
        App.getAppComponent().inject(this);
    }

    @Inject
    SettingsManager mSettingsManager;

    public Observable<String> getUnits() {
        return mSettingsManager.getTemperatureUnitsObservable();
    }

    public Observable<Integer> getUpdateInterval() {
        return mSettingsManager.getUpdateIntervalObservable();
    }

    public void onChangeUnits(String units) {
        mSettingsManager.saveTemperatureUnits(units);
    }

    public void onChangeUpdateInterval(int interval) {
        mSettingsManager.saveUpdateInterval(interval);
    }
}
