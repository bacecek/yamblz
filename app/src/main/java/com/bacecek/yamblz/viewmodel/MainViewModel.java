package com.bacecek.yamblz.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.bacecek.yamblz.App;
import com.bacecek.yamblz.data.network.response.WeatherResponse;
import com.bacecek.yamblz.data.repository.weather.WeatherRepository;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Denis Buzmakov on 13.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class MainViewModel extends ViewModel {

    public MainViewModel() {
        App.getAppComponent().inject(this);
    }

    @Inject
    WeatherRepository mRepository;

    public Single<WeatherResponse> getCurrentWeather(String city) {
        return mRepository.getCurrentWeather(city)
                .observeOn(AndroidSchedulers.mainThread());
    }

}
