package com.bacecek.yamblz.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bacecek.yamblz.R;
import com.bacecek.yamblz.data.presentation.City;
import com.bacecek.yamblz.presenter.SettingsPresenter;
import com.bacecek.yamblz.ui.fragment.ChooseCityFragment;
import com.bacecek.yamblz.ui.fragment.ChooseUpdateIntervalFragment;
import com.bacecek.yamblz.ui.widget.SwitchTemperature;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity implements
        ChooseUpdateIntervalFragment.OnChooseUpdateIntervalListener,
        ChooseCityFragment.OnChooseCityListener,
        SettingsPresenter.SettingsView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.switch_temp)
    SwitchTemperature switchTemperature;
    @BindView(R.id.txt_settings_period)
    TextView txtUpdatePeriod;
    @BindView(R.id.txt_city)
    TextView txtCity;

    private SettingsPresenter presenter;

    @OnClick(R.id.settings_temp_units)
    void onClickSwitchTempUnits() {
        switch (switchTemperature.getState()) {
            case SwitchTemperature.State.CELSIUS:
                presenter.onChangeTempUnits(getString(R.string.imperial));
                break;
            case SwitchTemperature.State.FAHRENHEIT:
                presenter.onChangeTempUnits(getString(R.string.metric));
                break;
        }
    }

    @OnClick(R.id.settings_interval_update)
    void onClickChooseInterval() {
        ChooseUpdateIntervalFragment.newInstance().show(getSupportFragmentManager(), "choose interval dialog");
    }

    @OnClick(R.id.settings_city)
    void onClickChooseCity() {
        ChooseCityFragment.newInstance().show(getSupportFragmentManager(), "choose city dialog");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setTitle(R.string.action_settings);
        presenter = ViewModelProviders.of(this).get(SettingsPresenter.class);
        presenter.onAttach(this);

        initToolbar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    public void updateTempUnits(String units) {
        if (units.equals(getString(R.string.metric))) {
            switchTemperature.setState(SwitchTemperature.State.CELSIUS);
        } else if (units.equals(getString(R.string.imperial))) {
            switchTemperature.setState(SwitchTemperature.State.FAHRENHEIT);
        }
    }

    @Override
    public void updateInterval(int interval) {
        int positionInArrayOfIntervals = -1;
        int[] intervalArrayValues = getResources().getIntArray(R.array.array_intervals_values);
        String[] intervalArrayTitles = getResources().getStringArray(R.array.array_intervals_titles);
        for (int i = 0; i < intervalArrayValues.length; i++) {
            if (interval == intervalArrayValues[i]) {
                positionInArrayOfIntervals = i;
                break;
            }
        }
        if (positionInArrayOfIntervals != -1) {
            String intervalTitle = intervalArrayTitles[positionInArrayOfIntervals];
            txtUpdatePeriod.setText(intervalTitle);
        }
    }

    @Override
    public void updateCity(String city) {
        txtCity.setText(city);
    }

    @Override
    public void onChooseInterval(int interval) {
        presenter.onChangeUpdateInterval(interval);
    }

    @Override
    public void onChooseCity(@NonNull City city) {
        presenter.onChangeCity(city.getName());
    }
}
