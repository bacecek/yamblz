package com.bacecek.yamblz;

import android.content.SharedPreferences;

import com.bacecek.yamblz.data.network.PlacesApi;
import com.bacecek.yamblz.data.network.WeatherApi;
import com.bacecek.yamblz.data.repository.cache.WeatherCache;
import com.bacecek.yamblz.data.repository.places.PlacesRepository;
import com.bacecek.yamblz.data.repository.settings.SettingsManager;
import com.bacecek.yamblz.data.repository.weather.WeatherRepository;
import com.bacecek.yamblz.util.AppResources;
import com.bacecek.yamblz.util.Utils;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public abstract class AbstractTestWithServicesAndResources extends AbstractTestWithResources {

    @Rule
    public TestSchedulerRule testSchedulerRule = new TestSchedulerRule();

    @Mock
    protected WeatherApi weatherApi;

    @Mock
    protected WeatherRepository weatherRepository;

    @Mock
    protected SettingsManager settingsManager;

    @Mock
    protected WeatherCache weatherCache;

    @Mock
    protected PlacesApi placesApi;

    @Mock
    protected PlacesRepository placesRepository;

    @Mock
    protected Utils utils;

    @Mock
    protected SharedPreferences preferences;

    @Mock
    protected RxSharedPreferences rxPreferences;

    @Mock
    protected AppResources resources;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }
}
