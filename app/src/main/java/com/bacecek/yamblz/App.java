package com.bacecek.yamblz;

import android.app.Application;

import com.bacecek.yamblz.di.component.AppComponent;
import com.bacecek.yamblz.di.component.DaggerAppComponent;
import com.bacecek.yamblz.di.module.AppModule;
import com.bacecek.yamblz.di.module.NetworkModule;
import com.bacecek.yamblz.data.network.service.WeatherJobService;
import com.bacecek.yamblz.util.Consts;
import com.facebook.stetho.Stetho;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import timber.log.Timber;

/**
 * Created by Denis Buzmakov on 12.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class App extends Application {
    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initDI(); //должно всегда быть первым, т.к. после него идет иницизация и запуск сервиса, в котором нужен DI
        initLibraries();
        startWeatherService();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    private void initLibraries() {
        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(getApplicationContext());
        }
    }

    /**
     * Инициализация Dagger'a
     */
    private void initDI() {
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    /**
     * Запуск сервиса обновления погоды
     */
    private void startWeatherService() {
        //TODO:проверка включения сервиса в настройках
        sAppComponent.getSettings().getUpdateIntervalObservable()
                .subscribe(interval -> {
                    Timber.d("Update interval: " + String.valueOf(interval));
                    FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getApplicationContext()));
                    dispatcher.cancelAll(); //отменить все существующие, чтобы они не работали параллельно и не создавали кашу
                    Job job = dispatcher.newJobBuilder()
                            .setService(WeatherJobService.class)
                            .setTag(Consts.TAG_WEATHER_SERVICE)
                            .setLifetime(Lifetime.FOREVER)
                            .setConstraints(Constraint.ON_ANY_NETWORK)
                            .setRecurring(true)
                            .setReplaceCurrent(true)
                            .setTrigger(Trigger.executionWindow((int) (interval / 1.5), (int) (interval * 1.5)))
                            .build();
                    dispatcher.mustSchedule(job);
                });
    }
}
