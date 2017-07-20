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
        void updateTempUnits(String units);
        void updateInterval(int interval);
    }

    public SettingsPresenter() {
        App.getAppComponent().inject(this);
    }

    @Inject
    SettingsManager settingsManager;

    private String currentTempUnits;
    private int currentInterval = Integer.MIN_VALUE;

    @Override
    public void onAttach(SettingsView view) {
        super.onAttach(view);

        if(currentTempUnits != null) view.updateTempUnits(currentTempUnits);
        else getTempUnits();

        if(currentInterval != Integer.MIN_VALUE) view.updateInterval(currentInterval);
        else getUpdateInterval();
    }

    private void getTempUnits() {
        settingsManager.getTemperatureUnitsObservable()
                .subscribe(units -> {
                    currentTempUnits = units;
                    if(getView() != null) {
                        getView().updateTempUnits(units);
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

    public void onChangeTempUnits(String units) {
        settingsManager.saveTemperatureUnits(units);
    }

    public void onChangeUpdateInterval(int interval) {
        settingsManager.saveUpdateInterval(interval);
    }
}
