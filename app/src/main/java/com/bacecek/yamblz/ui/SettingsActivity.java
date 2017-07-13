package com.bacecek.yamblz.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.bacecek.yamblz.R;
import com.bacecek.yamblz.ui.widget.SwitchTemperature;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.switch_temp)
    SwitchTemperature mSwitchTemp;

    @OnClick(R.id.settings_units)
    void onClickSwitchUnits() {
        mSwitchTemp.switchState();
    }

    @OnClick(R.id.settings_interval_update)
    void onClickChooseInterval() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setTitle(R.string.action_settings);

        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(view -> onBackPressed());
    }
}
