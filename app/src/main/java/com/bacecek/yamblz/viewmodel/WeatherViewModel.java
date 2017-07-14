package com.bacecek.yamblz.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.bacecek.yamblz.App;
import com.bacecek.yamblz.data.presentation.WeatherInfo;
import com.bacecek.yamblz.data.repository.settings.SettingsManager;
import com.bacecek.yamblz.data.repository.weather.WeatherRepository;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Denis Buzmakov on 13.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class WeatherViewModel extends ViewModel {

    public WeatherViewModel() {
        App.getAppComponent().inject(this);
    }

    @Inject
    WeatherRepository mWeatherRepository;

    @Inject
    SettingsManager mSettingsManager;

    public Single<WeatherInfo> getCurrentWeather(String city) {
        return mWeatherRepository.getCurrentWeather(city)
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> new WeatherInfo(
                        result.getInfo().getCurrentTemperature(),
                        result.getConditions().get(0).getConditionId(),
                        result.getInfo().getHumidity(),
                        result.getWindInfo().getSpeed(),
                        result.getInternalInfo().getSunrise()));
    }

}
