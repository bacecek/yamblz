package com.bacecek.yamblz.ui.fragment;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bacecek.yamblz.R;
import com.bacecek.yamblz.data.presentation.WeatherInfo;
import com.bacecek.yamblz.viewmodel.WeatherViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Denis Buzmakov on 13.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class WeatherFragment extends LifecycleFragment {
    private static final String KEY_WEATHER = "current_weather";
    private WeatherViewModel viewModel;
    private WeatherInfo currentWeatherInfo;

    @BindView(R.id.txt_city)
    TextView txtCity;
    @BindView(R.id.txt_update_date)
    TextView txtUpdateDate;
    @BindView(R.id.txt_temperature)
    TextView txtTemperature;
    @BindView(R.id.txt_description)
    TextView txtDescription;
    @BindView(R.id.txt_sunrise_value)
    TextView txtSunriseDate;
    @BindView(R.id.txt_wind_value)
    TextView txtWindSpeed;
    @BindView(R.id.txt_humidity_value)
    TextView txtHumidity;
    @BindView(R.id.img_weather_condition)
    ImageView imgWeatherCondition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);

        if(savedInstanceState != null) {
            currentWeatherInfo = savedInstanceState.getParcelable(KEY_WEATHER);
        }
        if(currentWeatherInfo == null) {
            viewModel.getCurrentWeather(getString(R.string.moscow_request))
                    .subscribe(result -> {
                        currentWeatherInfo = result;
                        showWeather(result);
                    });
        } else {
            showWeather(currentWeatherInfo);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(KEY_WEATHER, currentWeatherInfo);
    }

    public void showWeather(@NonNull WeatherInfo weatherInfo) {
        txtCity.setText(getString(R.string.moscow));//TODO:change city from response in the future
        txtUpdateDate.setText("");//TODO:сделать получение даты обновления погоды
        txtTemperature.setText(String.valueOf(weatherInfo.getCurrentTemperature()));//TODO: вынести в ресурсы отображение погоды
        txtDescription.setText("");//TODO:сделать дескрипшн на русском (на устройстве, а не с сервера)
        txtSunriseDate.setText(String.valueOf(weatherInfo.getSunriseTime()));//TODO:сделать нормально отображение времени, преобразование мб в VM
        txtWindSpeed.setText(String.valueOf(weatherInfo.getWindSpeed()));//TODO:сделать форматирование с 1 цифрой после запятой
        txtHumidity.setText(String.valueOf(weatherInfo.getHumidity()));//TODO:допилить отображение влажности
        //TODO:в зависимости от состояния погоды задавать картинку
    }

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }
}
