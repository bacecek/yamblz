package com.bacecek.yamblz.di.module;

import android.content.SharedPreferences;

import com.bacecek.yamblz.BuildConfig;
import com.bacecek.yamblz.data.network.PlacesApi;
import com.bacecek.yamblz.data.network.WeatherApi;
import com.bacecek.yamblz.data.network.util.KeyInterceptor;
import com.bacecek.yamblz.data.repository.places.PlacesRepository;
import com.bacecek.yamblz.data.repository.places.PlacesRepositoryImpl;
import com.bacecek.yamblz.data.repository.settings.SettingsManager;
import com.bacecek.yamblz.data.repository.weather.WeatherRepository;
import com.bacecek.yamblz.data.repository.weather.WeatherRepositoryImpl;
import com.bacecek.yamblz.util.AppResources;
import com.bacecek.yamblz.util.Utils;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Denis Buzmakov on 12.07.2017.
 * <buzmakov.da@gmail.com>
 */


@Module
public class NetworkModule {

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    //region weather

    @Provides
    @Singleton
    @Named("weather")
    Interceptor provideOkHttpInterceptor() {
        return new KeyInterceptor("appid", BuildConfig.API_WEATHER_KEY);
    }

    @Provides
    @Singleton
    @Named("weather")
    OkHttpClient provideOkHttpClient(@Named("weather") Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    @Provides
    @Singleton
    @Named("weather")
    Retrofit provideRetrofit(@Named("weather") OkHttpClient client, GsonConverterFactory factory) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_WEATHER_URL)
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    WeatherApi provideWeatherApi(@Named("weather") Retrofit retrofit) {
        return retrofit.create(WeatherApi.class);
    }

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository(Utils utils,
                                               WeatherApi api,
                                               RxSharedPreferences rxPreferences,
                                               SharedPreferences preferences,
                                               AppResources resources,
                                               SettingsManager settingsManager) {
        return new WeatherRepositoryImpl(
                utils,
                api,
                rxPreferences,
                preferences,
                resources,
                settingsManager
        );
    }

    //endregion

    //region places

    @Provides
    @Singleton
    @Named("places")
    Interceptor providePlacesOkHttpInterceptor() {
        return new KeyInterceptor("key", BuildConfig.API_PLACES_KEY);
    }

    @Provides
    @Singleton
    @Named("places")
    OkHttpClient providePlacesOkHttpClient(@Named("places") Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    @Provides
    @Singleton
    @Named("places")
    Retrofit providePlacesRetrofit(@Named("places") OkHttpClient client, GsonConverterFactory factory) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_PLACES_URL)
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    PlacesApi providePlcaesApi(@Named("places") Retrofit retrofit) {
        return retrofit.create(PlacesApi.class);
    }

    @Provides
    @Singleton
    PlacesRepository providePlacesRepository(PlacesApi api,
                                             SettingsManager settingsManager) {
        return new PlacesRepositoryImpl(api, settingsManager);
    }

    //endregion
}
