package com.bacecek.yamblz.data.network.service;

import com.bacecek.yamblz.App;
import com.bacecek.yamblz.data.repository.settings.SettingsManager;
import com.bacecek.yamblz.data.repository.weather.WeatherRepository;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Created by Denis Buzmakov on 12.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class WeatherJobService extends JobService {

    public WeatherJobService() {
        App.getAppComponent().inject(this);
    }

    @Inject
    WeatherRepository weatherRepository;
    @Inject
    SettingsManager settingsManager;

    @Override
    public boolean onStartJob(JobParameters job) {
        Timber.d("weather job started");
        weatherRepository.getCurrentWeather()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    weatherRepository.saveLastWeather(response);
                    jobFinished(job, false);
                }, error -> {
                    Timber.d(error.getMessage());
                    jobFinished(job, false);
                });
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Timber.d("weather job stopped");
        return false;
    }


}
