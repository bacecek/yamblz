package com.bacecek.yamblz.data.repository.weather;

import com.bacecek.yamblz.AbstractTestWithServicesAndResources;
import com.bacecek.yamblz.data.network.response.ConditionResponse;
import com.bacecek.yamblz.data.network.response.InternalInfoResponse;
import com.bacecek.yamblz.data.network.response.WeatherInfoResponse;
import com.bacecek.yamblz.data.network.response.WeatherResponse;
import com.bacecek.yamblz.data.network.response.WindResponse;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

/**
 * Created by vandrikeev on 30.07.17.
 */
public class WeatherRepositoryImplTest extends AbstractTestWithServicesAndResources {

    private WeatherRepository repository;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        repository = new WeatherRepositoryImpl(utils, weatherApi, weatherCache, settingsManager);
    }

    @Test
    public void getWeather_Online_OK() throws Exception {
        Mockito.when(utils.isOnline()).thenReturn(true);
        Mockito.when(settingsManager.getCityCoordsObservable()).thenReturn(Observable.just(moscowCoords));
        Mockito.when(weatherApi.getCurrentWeather(moscowCoords.getLongitude(), moscowCoords.getLatitude()))
                .thenReturn(Single.just(getWeatherMoscowOK()));

        TestObserver<WeatherResponse> observer = new TestObserver<>();
        repository.getCurrentWeather().subscribe(observer);

        observer.assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .assertValue(weatherResponse -> {

                    Assert.assertEquals(1501414200, weatherResponse.getUpdateTime());

                    WeatherInfoResponse info = weatherResponse.getInfo();
                    Assert.assertEquals(294.91, info.getCurrentTemperature(), 0);
                    Assert.assertEquals(293.15, info.getMinTemperature(), 0);
                    Assert.assertEquals(296.15, info.getMaxTemperature(), 0);
                    Assert.assertEquals(78, info.getHumidity());
                    Assert.assertEquals(1004, info.getPressure());

                    Assert.assertEquals(1, weatherResponse.getConditions().size());

                    ConditionResponse condition = weatherResponse.getConditions().get(0);
                    Assert.assertEquals("09d", condition.getConditionIcon());
                    Assert.assertEquals("light intensity shower rain", condition.getDescription());
                    Assert.assertEquals(520, condition.getConditionId());

                    Assert.assertEquals(75, weatherResponse.getCloudsInfo().getCloudiness());

                    WindResponse windInfo = weatherResponse.getWindInfo();
                    Assert.assertEquals(6, windInfo.getSpeed(), 0);
                    Assert.assertEquals(100, windInfo.getDirection(), 0);

                    InternalInfoResponse internalInfo = weatherResponse.getInternalInfo();
                    Assert.assertEquals(1501378316, internalInfo.getSunrise());
                    Assert.assertEquals(1501436320, internalInfo.getSunset());

                    return true;
                });
    }

    @Test
    public void getWeather_Offline_OK() throws Exception {
        Mockito.when(utils.isOnline()).thenReturn(false);
        Mockito.when(weatherCache.loadCache()).thenReturn(Observable.just(getWeatherMoscowOK()));

        TestObserver<WeatherResponse> observer = new TestObserver<>();
        repository.getCurrentWeather().subscribe(observer);

        observer.assertSubscribed()
                .assertComplete()
                .assertNoErrors()
                .assertValue(weatherResponse -> {

                    Assert.assertEquals(1501414200, weatherResponse.getUpdateTime());

                    WeatherInfoResponse info = weatherResponse.getInfo();
                    Assert.assertEquals(294.91, info.getCurrentTemperature(), 0);
                    Assert.assertEquals(293.15, info.getMinTemperature(), 0);
                    Assert.assertEquals(296.15, info.getMaxTemperature(), 0);
                    Assert.assertEquals(78, info.getHumidity());
                    Assert.assertEquals(1004, info.getPressure());

                    Assert.assertEquals(1, weatherResponse.getConditions().size());

                    ConditionResponse condition = weatherResponse.getConditions().get(0);
                    Assert.assertEquals("09d", condition.getConditionIcon());
                    Assert.assertEquals("light intensity shower rain", condition.getDescription());
                    Assert.assertEquals(520, condition.getConditionId());

                    Assert.assertEquals(75, weatherResponse.getCloudsInfo().getCloudiness());

                    WindResponse windInfo = weatherResponse.getWindInfo();
                    Assert.assertEquals(6, windInfo.getSpeed(), 0);
                    Assert.assertEquals(100, windInfo.getDirection(), 0);

                    InternalInfoResponse internalInfo = weatherResponse.getInternalInfo();
                    Assert.assertEquals(1501378316, internalInfo.getSunrise());
                    Assert.assertEquals(1501436320, internalInfo.getSunset());

                    return true;
                });
    }

    @Test
    public void getWeather_Online_Error() throws Exception {
        Mockito.when(utils.isOnline()).thenReturn(true);
        Mockito.when(settingsManager.getCityCoordsObservable()).thenReturn(Observable.just(moscowCoords));
        Mockito.when(weatherApi.getCurrentWeather(moscowCoords.getLongitude(), moscowCoords.getLatitude()))
                .thenReturn(Single.error(networkError));

        TestObserver<WeatherResponse> observer = new TestObserver<>();
        repository.getCurrentWeather().subscribe(observer);

        observer.assertSubscribed()
                .assertNoValues()
                .assertError(networkError);
    }

    @Test
    public void getWeather_CacheWeather() throws Exception {
        WeatherResponse weather = getWeatherMoscowOK();
        repository.saveLastWeather(weather);
        Mockito.verify(weatherCache, Mockito.times(1)).cacheWeather(weather);
    }
}
