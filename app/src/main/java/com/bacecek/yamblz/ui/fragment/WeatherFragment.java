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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bacecek.yamblz.R;
import com.bacecek.yamblz.data.presentation.WeatherInfo;
import com.bacecek.yamblz.presenter.WeatherPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.layout_weather_info)
    View layoutWeather;
    @BindView(R.id.layout_empty)
    View layoutEmpty;

    @OnClick(R.id.btn_try_again)
    void onClickTryAgain() {
        presenter.onTryAgain();
    }

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
        layoutWeather.setVisibility(View.VISIBLE);

        txtCity.setText(getString(R.string.moscow));//TODO:change city from response in the future
        txtUpdateTime.setText(weatherInfo.getUpdateTime());
        txtTemperature.setText(weatherInfo.getCurrentTemperature());
        txtDescription.setText(weatherInfo.getDescription());//TODO:get this from resources
        txtSunriseDate.setText(weatherInfo.getSunriseTime());
        txtWindSpeed.setText(getString(R.string.template_wind_speed, weatherInfo.getWindSpeed()));
        txtHumidity.setText(getString(R.string.template_humidity, weatherInfo.getHumidity()));

        int drawableCondition = 0;
        String conditionIcon = weatherInfo.getConditionIcon();
        if(conditionIcon.equals(getString(R.string.condition_sun_day))) {
            drawableCondition = R.drawable.condition_sun;
        } else if(conditionIcon.equals(getString(R.string.condition_sun_night))) {
            drawableCondition = R.drawable.condition_moon;
        } else if(conditionIcon.equals(getString(R.string.condition_sun_clouds_day))) {
            drawableCondition = R.drawable.condition_sun_clouds;
        } else if(conditionIcon.equals(getString(R.string.condition_sun_clouds_night))) {
            drawableCondition = R.drawable.condition_moon_clouds;
        } else if(conditionIcon.equals(getString(R.string.condition_clouds_day)) || conditionIcon.equals(getString(R.string.condition_clouds_night)) ||
                conditionIcon.equals(getString(R.string.condition_broken_clouds_day)) || conditionIcon.equals(getString(R.string.condition_broken_clouds_night))) {
            drawableCondition = R.drawable.condition_clouds;
        } else if(conditionIcon.equals(getString(R.string.condition_rain_day)) || conditionIcon.equals(getString(R.string.condition_rain_night))) {
            drawableCondition = R.drawable.condition_rain;
        } else if(conditionIcon.equals(getString(R.string.condition_sun_rain_day))) {
            drawableCondition = R.drawable.condition_sun_rain;
        } else if(conditionIcon.equals(getString(R.string.condition_sun_rain_night))) {
            drawableCondition = R.drawable.condition_moon_rain;
        } else if(conditionIcon.equals(getString(R.string.condition_storm_day)) || conditionIcon.equals(getString(R.string.condition_storm_night))) {
            drawableCondition = R.drawable.condition_storm;
        } else if(conditionIcon.equals(getString(R.string.condition_snow_day)) || conditionIcon.equals(getString(R.string.condition_snow_night))) {
            drawableCondition = R.drawable.condition_snow;
        } else if(conditionIcon.equals(getString(R.string.condition_mist_day)) || conditionIcon.equals(getString(R.string.condition_mist_night))) {
            drawableCondition = R.drawable.condition_mist;
        }
        if(drawableCondition != 0) {
            imgWeatherCondition.setImageResource(drawableCondition);
        }
    }

    @Override
    public void hideWeatherInfo() {
        layoutWeather.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        layoutEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        layoutEmpty.setVisibility(View.GONE);
    }

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }
}
