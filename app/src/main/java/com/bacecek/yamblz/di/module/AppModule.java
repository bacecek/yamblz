package com.bacecek.yamblz.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bacecek.yamblz.data.repository.settings.SettingsManager;
import com.bacecek.yamblz.data.repository.settings.SettingsManagerImpl;
import com.bacecek.yamblz.util.AppResources;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Denis Buzmakov on 12.07.2017.
 * <buzmakov.da@gmail.com>
 */


@Module
public class AppModule {
    private Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    RxSharedPreferences provideRxSharedPreferences(SharedPreferences preferences) {
        return RxSharedPreferences.create(preferences);
    }

    @Provides
    @Singleton
    AppResources provideResources(Context context) {
        return new AppResources(context);
    }

    @Provides
    @Singleton
    SettingsManager provideSettings(SharedPreferences preferences, RxSharedPreferences rxPreferences, AppResources resources) {
        return new SettingsManagerImpl(preferences, rxPreferences, resources);
    }
}
