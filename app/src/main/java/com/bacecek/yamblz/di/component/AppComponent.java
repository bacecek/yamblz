package com.bacecek.yamblz.di.component;

import com.bacecek.yamblz.di.module.AppModule;
import com.bacecek.yamblz.di.module.NetworkModule;
import com.bacecek.yamblz.data.network.service.WeatherJobService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Denis Buzmakov on 12.07.2017.
 * <buzmakov.da@gmail.com>
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    void inject(WeatherJobService service);
}
