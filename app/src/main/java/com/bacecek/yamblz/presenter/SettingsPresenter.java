package com.bacecek.yamblz.presenter;

import com.bacecek.yamblz.App;
import com.bacecek.yamblz.data.repository.settings.SettingsManager;
import com.bacecek.yamblz.ui.BaseView;

import javax.inject.Inject;

/**
 * Created by Denis Buzmakov on 13.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class SettingsPresenter extends BasePresenter<SettingsPresenter.SettingsView> {

    public interface SettingsView extends BaseView {
        void updateUnits(String units);
        void updateInterval(int interval);
    }

    public SettingsPresenter() {
        App.getAppComponent().inject(this);
    }

    @Inject
    SettingsManager settingsManager;

    private String currentUnits;
    private int currentInterval = Integer.MIN_VALUE;

    @Override
    public void onAttach(SettingsView view) {
        super.onAttach(view);

        if(currentUnits != null) view.updateUnits(currentUnits);
        else getUnits();

        if(currentInterval != Integer.MIN_VALUE) view.updateInterval(currentInterval);
        else getUpdateInterval();
    }

    private void getUnits() {
        settingsManager.getTemperatureUnitsObservable()
                .subscribe(units -> {
                    currentUnits = units;
                    if(getView() != null) {
                        getView().updateUnits(units);
                    }
                });
    }

    private void getUpdateInterval() {
        settingsManager.getUpdateIntervalObservable()
                .subscribe(interval -> {
                    currentInterval = interval;
                    if(getView() != null) {
                        getView().updateInterval(interval);
                    }
                });
    }

    public void onChangeUnits(String units) {
        settingsManager.saveTemperatureUnits(units);
    }

    public void onChangeUpdateInterval(int interval) {
        settingsManager.saveUpdateInterval(interval);
    }
}
