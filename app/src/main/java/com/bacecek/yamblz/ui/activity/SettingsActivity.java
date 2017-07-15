package com.bacecek.yamblz.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bacecek.yamblz.R;
import com.bacecek.yamblz.presenter.SettingsPresenter;
import com.bacecek.yamblz.ui.widget.SwitchTemperature;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.switch_temp)
    SwitchTemperature mSwitchTemp;
    @BindView(R.id.txt_settings_period)
    TextView mTxtUpdatePeriod;

    @OnClick(R.id.settings_units)
    void onClickSwitchUnits() {
        switch (mSwitchTemp.getState()) {
            case SwitchTemperature.State.CELSIUS:
                mViewModel.onChangeUnits(getString(R.string.imperial));
                break;
            case SwitchTemperature.State.FAHRENHEIT:
                mViewModel.onChangeUnits(getString(R.string.metric));
                break;
        }
    }

    @OnClick(R.id.settings_interval_update)
    void onClickChooseInterval() {
        mDialogUpdateInterval.show();
    }

    private SettingsPresenter mViewModel;
    private AlertDialog.Builder mDialogUpdateInterval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setTitle(R.string.action_settings);
        mViewModel = ViewModelProviders.of(this).get(SettingsPresenter.class);

        initToolbar();
        initUI();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void initUI() {
        mViewModel.getUnits()
                .subscribe(this::updateUnits);
        mViewModel.getUpdateInterval()
                .subscribe(this::updateInterval);
        mDialogUpdateInterval = new AlertDialog.Builder(this)
                .setItems(R.array.array_intervals_titles, (dialog, which) -> {
                    int[] intervalArrayValues = getResources().getIntArray(R.array.array_intervals_values);
                    mViewModel.onChangeUpdateInterval(intervalArrayValues[which]);
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                });
    }

    private void updateUnits(String units) {
        if(units.equals(getString(R.string.metric))) {
            mSwitchTemp.setState(SwitchTemperature.State.CELSIUS);
        } else if(units.equals(getString(R.string.imperial))) {
            mSwitchTemp.setState(SwitchTemperature.State.FAHRENHEIT);
        }
    }

    /**
     * Обновление
     * @param interval
     */
    private void updateInterval(int interval) {
        int positionInArrayOfIntervals = -1;
        int[] intervalArrayValues = getResources().getIntArray(R.array.array_intervals_values);
        String[] intervalArrayTitles = getResources().getStringArray(R.array.array_intervals_titles);
        for(int i = 0; i < intervalArrayValues.length; i++) {
            if(interval == intervalArrayValues[i]) {
                positionInArrayOfIntervals = i;
                break;
            }
        }
        if(positionInArrayOfIntervals != -1) {
            String intervalTitle = intervalArrayTitles[positionInArrayOfIntervals];
            mTxtUpdatePeriod.setText(intervalTitle);
        }
    }
}
