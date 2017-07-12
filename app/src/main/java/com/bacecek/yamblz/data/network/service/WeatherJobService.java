package com.bacecek.yamblz.data.network.service;

import com.bacecek.yamblz.App;
import com.bacecek.yamblz.data.network.WeatherApi;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
    WeatherApi mApi;

    @Override
    public boolean onStartJob(JobParameters job) {
        Timber.d("weather job started");
        mApi.getCurrentWeather("moscow")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Timber.d(String.valueOf(response.getInfo().getCurrentTemperature()));
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
