package com.bacecek.yamblz.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bacecek.yamblz.App;
import com.bacecek.yamblz.R;
import com.bacecek.yamblz.data.network.response.WeatherResponse;
import com.bacecek.yamblz.data.presentation.WeatherInfo;
import com.bacecek.yamblz.data.repository.settings.SettingsManager;
import com.bacecek.yamblz.data.repository.weather.WeatherRepository;
import com.bacecek.yamblz.ui.BaseView;
import com.bacecek.yamblz.util.AppResources;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Denis Buzmakov on 13.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class WeatherPresenter extends BasePresenter<WeatherPresenter.WeatherView> {

    public interface WeatherView extends BaseView{
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
                    if(currentWeatherInfo != null && currentWeatherResponse != null) {
                        double currentTemperature = currentWeatherResponse.getInfo().getCurrentTemperature();
                        currentWeatherInfo.setCurrentTemperature(convertTemperature(currentTemperature, units));
                        if(getView() != null) {
                            getView().showWeatherInfo(currentWeatherInfo);
                        }
                    }
                });
    }

    @Inject
    WeatherRepository weatherRepository;

    @Inject
    SettingsManager settingsManager;

    @Inject
    AppResources resources;

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
        if(getView() != null) {
            getView().hideLoading();
        }
    }

    private void getCurrentWeather() {
        //if weather info exists, show it on view. otherwise, load from repository
        if(currentWeatherInfo != null) {
            if(getView() != null) {
                getView().showWeatherInfo(currentWeatherInfo);
            }
        } else {
            loadWeather();
        }
    }

    /**
     * called when user do poll-to-refresh
     */
    public void onRefresh() {
        if(getView() != null) {
            getView().showLoading();
        }
        loadWeather();
    }

    /**
     * load weather info from repository
     */
    private void loadWeather() {
        weatherRepository.getCurrentWeather(resources.getString(R.string.moscow_request))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(result -> {
                    weatherRepository.saveLastWeather(result);
                    currentWeatherResponse = result;
                })
                .map(result -> new WeatherInfo(
                        convertTemperature(result.getInfo().getCurrentTemperature(), settingsManager.getTemperatureUnits()),
                        result.getConditions().get(0).getConditionId(),
                        result.getInfo().getHumidity(),
                        result.getWindInfo().getSpeed(),
                        formatSunriseTime(result.getInternalInfo().getSunrise()),
                        formatUpdateTime(result.getUpdateTime())))
                .subscribe(result -> {
                    currentWeatherInfo = result;
                    if (getView() != null) {
                        getView().hideLoading();
                        getView().showWeatherInfo(result);
                    }
                });
    }

    /**
     * convert temperature from Kelvin to chosen units
     * @param temperature - temperature
     * @param units - units
     * @return - converted temperature
     */
    @Nullable
    private String convertTemperature(double temperature, @NonNull String units) {
        String result = null;
        if(units.equals(resources.getString(R.string.metric))) {
            double resultTemperature = temperature - 273.15;
            result = resources.getString(R.string.template_temperature_celsius, resultTemperature);
        } else if(units.equals(resources.getString(R.string.imperial))) {
            double resultTemperature = temperature * 9 / 5 - 459.67;
            result = resources.getString(R.string.template_temperature_fahrenheit, resultTemperature);
        }
        return result;
    }

    /**
     * convert sunrise time from Unix time to displayed
     * @param time - Unix time
     * @return - converted time
     */
    @Nullable
    private String formatSunriseTime(long time) {
        if(time == 0) return null;
        Date date = new Date(time * 1000);
        SimpleDateFormat convertFormat = new SimpleDateFormat("H:mm", Locale.getDefault());
        convertFormat.setTimeZone(TimeZone.getDefault());
        return convertFormat.format(date);
    }

    /**
     * convert weather update time from Unix time to displayed
     * @param time - Unix time
     * @return - converted time
     */
    @Nullable
    private String formatUpdateTime(long time) {
        if(time == 0) return null;
        Date date = new Date(time * 1000);
        SimpleDateFormat convertFormat = new SimpleDateFormat("EEEE H:mm", Locale.getDefault());
        convertFormat.setTimeZone(TimeZone.getDefault());
        return convertFormat.format(date);
    }

}
