package com.bacecek.yamblz.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bacecek.yamblz.R;
import com.bacecek.yamblz.ui.fragment.WeatherFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.layout_drawer)
    DrawerLayout mDrawerLayout;

    @OnClick(R.id.txt_about)
    void onClickAbout() {
        startActivity(new Intent(this, AboutActivity.class));
    }

    @OnClick(R.id.txt_settings)
    void onClickSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private ActionBarDrawerToggle mActionBarToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initToolbar();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, WeatherFragment.newInstance())
                    .commit();
        }
    }

    /**
     * Инициализация тулбара
     */
    private void initToolbar() {
        setSupportActionBar(mToolbar);
        setTitle(null);
        mActionBarToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mActionBarToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarToggle.onConfigurationChanged(newConfig);
    }
}
