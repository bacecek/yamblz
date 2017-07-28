package com.bacecek.yamblz.presenter;

import android.support.annotation.NonNull;

import com.bacecek.yamblz.App;
import com.bacecek.yamblz.R;
import com.bacecek.yamblz.data.network.response.WeatherResponse;
import com.bacecek.yamblz.data.presentation.WeatherInfo;
import com.bacecek.yamblz.data.repository.settings.SettingsManager;
import com.bacecek.yamblz.data.repository.weather.WeatherRepository;
import com.bacecek.yamblz.ui.BaseView;
import com.bacecek.yamblz.util.AppResources;
import com.bacecek.yamblz.util.Utils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Denis Buzmakov on 13.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class WeatherPresenter extends BasePresenter<WeatherPresenter.WeatherView> {

    public interface WeatherView extends BaseView {
        void showWeatherInfo(@NonNull WeatherInfo weatherInfo);

        void hideWeatherInfo();

        void showLoading();

        void hideLoading();

        void showEmptyView();

        void hideEmptyView();
    }

    public WeatherPresenter() {
        App.getAppComponent().inject(this);
        settingsManager.getTemperatureUnitsObservable()
                .subscribe(units -> {
                    if (currentWeatherInfo != null && currentWeatherResponse != null) {
                        double currentTemperature = currentWeatherResponse.getInfo().getCurrentTemperature();
                        currentWeatherInfo.setCurrentTemperature(weatherUtils.convertAndFormatTemperatureFromKelvin(currentTemperature, units));
                        if (getView() != null) {
                            getView().showWeatherInfo(currentWeatherInfo);
                        }
                    }
                });
        settingsManager.getCityCoordsObservable().subscribe(it -> loadWeather());
    }

    @Inject
    WeatherRepository weatherRepository;

    @Inject
    SettingsManager settingsManager;

    @Inject
    AppResources resources;

    @Inject
    Utils weatherUtils;

    private WeatherInfo currentWeatherInfo;
    private WeatherResponse currentWeatherResponse;

    @Override
    public void onAttach(WeatherView view) {
        super.onAttach(view);
        getCurrentWeather();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getView() != null) {
            getView().hideLoading();
        }
    }

    private void getCurrentWeather() {
        //if weather info exists, show it on view. otherwise, load from repository
        if (currentWeatherInfo != null) {
            if (getView() != null) {
                getView().showWeatherInfo(currentWeatherInfo);
            }
        } else {
            loadWeather();
        }
    }

    /**
     * called when user do pull-to-refresh
     */
    public void onRefresh() {
        loadWeather();
    }

    /**
     * load weather info from repository
     */
    private void loadWeather() {
        weatherRepository.getCurrentWeather()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(result -> {
                    weatherRepository.saveLastWeather(result);
                    currentWeatherResponse = result;
                })
                .map(result -> new WeatherInfo(
                        settingsManager.getCityName(),
                        weatherUtils.convertAndFormatTemperatureFromKelvin(result.getInfo().getCurrentTemperature(), settingsManager.getTemperatureUnits()),
                        result.getConditions().get(0).getConditionId(),
                        result.getConditions().get(0).getConditionIcon(),
                        result.getConditions().get(0).getDescription(),
                        result.getInfo().getHumidity(),
                        result.getWindInfo().getSpeed(),
                        weatherUtils.formatUnixTime(result.getInternalInfo().getSunrise(), resources.getString(R.string.format_sunrise)),
                        weatherUtils.formatUnixTime(result.getUpdateTime(), resources.getString(R.string.format_update_weather))))
                .subscribe(result -> {
                    currentWeatherInfo = result;
                    if (getView() != null) {
                        getView().hideLoading();
                        if (result == null) {
                            getView().showEmptyView();
                        } else {
                            getView().hideEmptyView();
                            getView().showWeatherInfo(result);
                        }
                    }
                }, error -> {
                    currentWeatherInfo = null;
                    if (getView() != null) {
                        getView().hideLoading();
                        getView().hideWeatherInfo();
                        getView().showEmptyView();
                    }
                });
    }

    public void onTryAgain() {
        if (getView() != null) {
            getView().hideEmptyView();
            getView().showLoading();
        }
        loadWeather();
    }

}
