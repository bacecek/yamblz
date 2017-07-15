package com.bacecek.yamblz.ui.fragment;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bacecek.yamblz.R;
import com.bacecek.yamblz.data.presentation.WeatherInfo;
import com.bacecek.yamblz.presenter.WeatherPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Denis Buzmakov on 13.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class WeatherFragment extends LifecycleFragment implements WeatherPresenter.WeatherView {
    private static final String KEY_WEATHER = "current_weather";
    private WeatherPresenter presenter;

    @BindView(R.id.txt_city)
    TextView txtCity;
    @BindView(R.id.txt_update_time)
    TextView txtUpdateTime;
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
    @BindView(R.id.layout_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, layout);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.onRefresh());
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = ViewModelProviders.of(this).get(WeatherPresenter.class);
        presenter.onAttach(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
    }

    @Override
    public void showWeatherInfo(@NonNull WeatherInfo weatherInfo) {
        txtCity.setText(getString(R.string.moscow));//TODO:change city from response in the future
        txtUpdateTime.setText(weatherInfo.getUpdateTime());
        txtTemperature.setText(weatherInfo.getCurrentTemperature());
        txtDescription.setText("");//TODO:сделать дескрипшн на русском (на устройстве, а не с сервера)
        txtSunriseDate.setText(weatherInfo.getSunriseTime());
        txtWindSpeed.setText(getString(R.string.template_wind_speed, weatherInfo.getWindSpeed()));
        txtHumidity.setText(getString(R.string.template_humidity, weatherInfo.getHumidity()));
        //TODO:в зависимости от состояния погоды задавать картинку
    }

    @Override
    public void hideWeatherInfo() {

    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void hideEmptyView() {

    }

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }
}
