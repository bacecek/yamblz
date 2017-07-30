package com.bacecek.yamblz.data.repository.settings;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.bacecek.yamblz.AbstractTestWithServicesAndResources;
import com.bacecek.yamblz.R;
import com.bacecek.yamblz.data.presentation.CityCoords;
import com.bacecek.yamblz.util.Consts;
import com.f2prateek.rx.preferences2.Preference;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.TestObserver;

/**
 * Created by vandrikeev on 30.07.17.
 */
public class SettingsManagerImplTest extends AbstractTestWithServicesAndResources {

    private static final String METRIC_UNITS = "metric";
    private static final int METRIC_UNITS_ID = R.string.metric;

    private SettingsManager settingsManager;

    @Mock
    private SharedPreferences.Editor editor;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        settingsManager = new SettingsManagerImpl(preferences, rxPreferences, resources);

        Mockito.when(preferences.edit()).thenReturn(editor);
        Mockito.when(editor.putString(Mockito.anyString(), Mockito.anyString())).thenReturn(editor);
        Mockito.when(editor.putInt(Mockito.anyString(), Mockito.any(Integer.class))).thenReturn(editor);
        Mockito.when(editor.putFloat(Mockito.anyString(), Mockito.any(Float.class))).thenReturn(editor);
    }

    @After
    public void reset() throws Exception {
        Mockito.clearInvocations(preferences);
        Mockito.clearInvocations(editor);
    }

    @Test
    public void getTempUnits_Plain() throws Exception {
        Mockito.when(resources.getString(METRIC_UNITS_ID)).thenReturn(METRIC_UNITS);
        Mockito.when(preferences.getString(Consts.Prefs.KEY_TEMP_UNITS, METRIC_UNITS)).thenReturn(METRIC_UNITS);

        Assert.assertEquals(METRIC_UNITS, settingsManager.getTemperatureUnits());
    }

    @Test
    public void getTempUnits_Observable() throws Exception {
        Mockito.when(resources.getString(METRIC_UNITS_ID)).thenReturn(METRIC_UNITS);
        Mockito.when(rxPreferences.getString(Consts.Prefs.KEY_TEMP_UNITS, METRIC_UNITS)).thenReturn(createTempUnitsPref());

        TestObserver<String> observer = new TestObserver<>();
        settingsManager.getTemperatureUnitsObservable().subscribe(observer);

        observer.assertSubscribed()
                .assertNoErrors()
                .assertValue(METRIC_UNITS);
    }

    @Test
    public void saveTempUnits() throws Exception {
        settingsManager.saveTemperatureUnits(METRIC_UNITS);

        Mockito.verify(preferences, Mockito.times(1)).edit();
        Mockito.verify(editor, Mockito.times(1)).putString(Consts.Prefs.KEY_TEMP_UNITS, METRIC_UNITS);
        Mockito.verify(editor, Mockito.times(1)).apply();
    }

    @Test
    public void getUpdateInterval_Plain() throws Exception {
        Mockito.when(preferences.getInt(Consts.Prefs.KEY_UPDATE_INTERVAL, Consts.DEFAULT_UPDATE_INTERVAL))
                .thenReturn(Consts.DEFAULT_UPDATE_INTERVAL);

        Assert.assertEquals(Consts.DEFAULT_UPDATE_INTERVAL, settingsManager.getUpdateInterval());
    }

    @Test
    public void getUpdateInterval_Observable() throws Exception {
        Mockito.when(rxPreferences.getInteger(Consts.Prefs.KEY_UPDATE_INTERVAL, Consts.DEFAULT_UPDATE_INTERVAL))
                .thenReturn(createIntervalPref());

        TestObserver<Integer> observer = new TestObserver<>();
        settingsManager.getUpdateIntervalObservable().subscribe(observer);

        observer.assertSubscribed()
                .assertNoErrors()
                .assertValue(Consts.DEFAULT_UPDATE_INTERVAL);
    }

    @Test
    public void saveUpdateInterval() throws Exception {
        settingsManager.saveUpdateInterval(Consts.DEFAULT_UPDATE_INTERVAL);

        Mockito.verify(preferences, Mockito.times(1)).edit();
        Mockito.verify(editor, Mockito.times(1)).putInt(Consts.Prefs.KEY_UPDATE_INTERVAL, Consts.DEFAULT_UPDATE_INTERVAL);
        Mockito.verify(editor, Mockito.times(1)).apply();
    }

    @Test
    public void getCityName_Plain() throws Exception {
        Mockito.when(preferences.getString(Consts.Prefs.KEY_CITY_NAME, null)).thenReturn(MOSCOW_CITY_NAME);

        Assert.assertEquals(MOSCOW_CITY_NAME, settingsManager.getCityName());
    }

    @Test
    public void getCityName_Observable() throws Exception {
        Mockito.when(rxPreferences.getString(Consts.Prefs.KEY_CITY_NAME)).thenReturn(createCityNamePref());

        TestObserver<String> observer = new TestObserver<>();
        settingsManager.getCityNameObservable().subscribe(observer);

        observer.assertSubscribed()
                .assertNoErrors()
                .assertValue(MOSCOW_CITY_NAME);
    }

    @Test
    public void saveCityName() throws Exception {
        settingsManager.saveCityName(MOSCOW_CITY_NAME);

        Mockito.verify(preferences, Mockito.times(1)).edit();
        Mockito.verify(editor, Mockito.times(1)).putString(Consts.Prefs.KEY_CITY_NAME, MOSCOW_CITY_NAME);
        Mockito.verify(editor, Mockito.times(1)).apply();
    }

    @Test
    public void getCityId_Plain() throws Exception {
        Mockito.when(preferences.getString(Consts.Prefs.KEY_CITY_ID, null)).thenReturn(MOSCOW_PLACE_ID);

        Assert.assertEquals(MOSCOW_PLACE_ID, settingsManager.getCityId());
    }

    @Test
    public void getCityId_Observable() throws Exception {
        Mockito.when(rxPreferences.getString(Consts.Prefs.KEY_CITY_ID)).thenReturn(createCityIdPref());

        TestObserver<String> observer = new TestObserver<>();
        settingsManager.getCityIdObservable().subscribe(observer);

        observer.assertSubscribed()
                .assertNoErrors()
                .assertValue(MOSCOW_PLACE_ID);
    }

    @Test
    public void saveCityId() throws Exception {
        settingsManager.saveCityId(MOSCOW_PLACE_ID);

        Mockito.verify(preferences, Mockito.times(1)).edit();
        Mockito.verify(editor, Mockito.times(1)).putString(Consts.Prefs.KEY_CITY_ID, MOSCOW_PLACE_ID);
        Mockito.verify(editor, Mockito.times(1)).apply();
    }

    @Test
    public void getCoords_Plain() throws Exception {
        Mockito.when(preferences.getFloat(Consts.Prefs.KEY_CITY_LON, Float.MIN_VALUE)).thenReturn(moscowCoords.getLongitude());
        Mockito.when(preferences.getFloat(Consts.Prefs.KEY_CITY_LAT, Float.MIN_VALUE)).thenReturn(moscowCoords.getLatitude());

        Assert.assertEquals(moscowCoords, settingsManager.getCityCoords());
    }

    @Test
    public void getCoords_Observable() throws Exception {
        Mockito.when(rxPreferences.getFloat(Consts.Prefs.KEY_CITY_LON)).thenReturn(createLonPref());
        Mockito.when(rxPreferences.getFloat(Consts.Prefs.KEY_CITY_LAT)).thenReturn(createLatPref());

        TestObserver<CityCoords> observer = new TestObserver<>();
        settingsManager.getCityCoordsObservable().subscribe(observer);

        observer.assertSubscribed()
                .assertNoErrors()
                .assertValue(moscowCoords);
    }

    @Test
    public void saveCoords() throws Exception {
        settingsManager.saveCityCoords(moscowCoords);

        Mockito.verify(preferences, Mockito.times(1)).edit();
        Mockito.verify(editor, Mockito.times(1)).putFloat(Consts.Prefs.KEY_CITY_LON, moscowCoords.getLongitude());
        Mockito.verify(editor, Mockito.times(1)).putFloat(Consts.Prefs.KEY_CITY_LAT, moscowCoords.getLatitude());
        Mockito.verify(editor, Mockito.times(1)).apply();
    }

    //region preferences

    private Preference<String> createTempUnitsPref() {
        return new Preference<String>() {
            @NonNull
            @Override
            public String key() {
                return Consts.Prefs.KEY_TEMP_UNITS;
            }

            @NonNull
            @Override
            public String defaultValue() {
                return METRIC_UNITS;
            }

            @NonNull
            @Override
            public String get() {
                return METRIC_UNITS;
            }

            @Override
            public void set(@NonNull String value) {

            }

            @Override
            public boolean isSet() {
                return true;
            }

            @Override
            public void delete() {

            }

            @NonNull
            @Override
            public Observable<String> asObservable() {
                return Observable.just(METRIC_UNITS);
            }

            @NonNull
            @Override
            public Consumer<? super String> asConsumer() {
                return null;
            }
        };
    }

    private Preference<Integer> createIntervalPref() {
        return new Preference<Integer>() {
            @NonNull
            @Override
            public String key() {
                return Consts.Prefs.KEY_UPDATE_INTERVAL;
            }

            @NonNull
            @Override
            public Integer defaultValue() {
                return Consts.DEFAULT_UPDATE_INTERVAL;
            }

            @NonNull
            @Override
            public Integer get() {
                return Consts.DEFAULT_UPDATE_INTERVAL;
            }

            @Override
            public void set(@NonNull Integer value) {

            }

            @Override
            public boolean isSet() {
                return false;
            }

            @Override
            public void delete() {

            }

            @NonNull
            @Override
            public Observable<Integer> asObservable() {
                return Observable.just(Consts.DEFAULT_UPDATE_INTERVAL);
            }

            @NonNull
            @Override
            public Consumer<? super Integer> asConsumer() {
                return null;
            }
        };
    }

    private Preference<String> createCityNamePref() {
        return new Preference<String>() {
            @NonNull
            @Override
            public String key() {
                return Consts.Prefs.KEY_CITY_NAME;
            }

            @NonNull
            @Override
            public String defaultValue() {
                return MOSCOW_CITY_NAME;
            }

            @NonNull
            @Override
            public String get() {
                return MOSCOW_CITY_NAME;
            }

            @Override
            public void set(@NonNull String value) {

            }

            @Override
            public boolean isSet() {
                return true;
            }

            @Override
            public void delete() {

            }

            @NonNull
            @Override
            public Observable<String> asObservable() {
                return Observable.just(MOSCOW_CITY_NAME);
            }

            @NonNull
            @Override
            public Consumer<? super String> asConsumer() {
                return null;
            }
        };
    }

    private Preference<String> createCityIdPref() {
        return new Preference<String>() {
            @NonNull
            @Override
            public String key() {
                return Consts.Prefs.KEY_CITY_ID;
            }

            @NonNull
            @Override
            public String defaultValue() {
                return MOSCOW_PLACE_ID;
            }

            @NonNull
            @Override
            public String get() {
                return MOSCOW_PLACE_ID;
            }

            @Override
            public void set(@NonNull String value) {

            }

            @Override
            public boolean isSet() {
                return true;
            }

            @Override
            public void delete() {

            }

            @NonNull
            @Override
            public Observable<String> asObservable() {
                return Observable.just(MOSCOW_PLACE_ID);
            }

            @NonNull
            @Override
            public Consumer<? super String> asConsumer() {
                return null;
            }
        };
    }

    private Preference<Float> createLatPref() {
        return new Preference<Float>() {
            @NonNull
            @Override
            public String key() {
                return Consts.Prefs.KEY_CITY_LAT;
            }

            @NonNull
            @Override
            public Float defaultValue() {
                return moscowCoords.getLatitude();
            }

            @NonNull
            @Override
            public Float get() {
                return moscowCoords.getLatitude();
            }

            @Override
            public void set(@NonNull Float value) {

            }

            @Override
            public boolean isSet() {
                return false;
            }

            @Override
            public void delete() {

            }

            @NonNull
            @Override
            public Observable<Float> asObservable() {
                return Observable.just(moscowCoords.getLatitude());
            }

            @NonNull
            @Override
            public Consumer<? super Float> asConsumer() {
                return null;
            }
        };
    }

    private Preference<Float> createLonPref() {
        return new Preference<Float>() {
            @NonNull
            @Override
            public String key() {
                return Consts.Prefs.KEY_CITY_LON;
            }

            @NonNull
            @Override
            public Float defaultValue() {
                return moscowCoords.getLongitude();
            }

            @NonNull
            @Override
            public Float get() {
                return moscowCoords.getLongitude();
            }

            @Override
            public void set(@NonNull Float value) {

            }

            @Override
            public boolean isSet() {
                return false;
            }

            @Override
            public void delete() {

            }

            @NonNull
            @Override
            public Observable<Float> asObservable() {
                return Observable.just(moscowCoords.getLongitude());
            }

            @NonNull
            @Override
            public Consumer<? super Float> asConsumer() {
                return null;
            }
        };
    }

    //endregion
}
