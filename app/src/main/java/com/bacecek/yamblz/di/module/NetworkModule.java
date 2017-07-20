package com.bacecek.yamblz.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.bacecek.yamblz.BuildConfig;
import com.bacecek.yamblz.data.network.util.KeyInterceptor;
import com.bacecek.yamblz.data.network.WeatherApi;
import com.bacecek.yamblz.data.repository.weather.WeatherRepository;
import com.bacecek.yamblz.data.repository.weather.WeatherRepositoryImpl;
import com.bacecek.yamblz.util.AppResources;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.facebook.stetho.okhttp3.StethoInterceptor;

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
    Interceptor provideOkHttpInterceptor() {
        return new KeyInterceptor(BuildConfig.API_WEATHER_KEY);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client, GsonConverterFactory factory) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_WEATHER_URL)
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    WeatherApi provideWeatherApi(Retrofit retrofit) {
        return retrofit.create(WeatherApi.class);
    }

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository(Context context,
                                               WeatherApi api,
                                               RxSharedPreferences rxPreferences,
                                               SharedPreferences preferences,
                                               AppResources resources) {
        return new WeatherRepositoryImpl(
                context,
                api,
                rxPreferences,
                preferences,
                resources
        );
    }

}
