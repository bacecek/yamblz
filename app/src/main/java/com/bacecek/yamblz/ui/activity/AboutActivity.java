package com.bacecek.yamblz.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.bacecek.yamblz.App;
import com.bacecek.yamblz.BuildConfig;
import com.bacecek.yamblz.R;
import com.bacecek.yamblz.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_version)
    TextView txtVersion;

    @OnClick(R.id.txt_source_code)
    void onClickSourceCode() {
        goToSite(getString(R.string.about_source_code));
    }

    @OnClick(R.id.txt_contact_me)
    void onClickContact() {
        String uriText =
                "mailto:" + getString(R.string.about_email) +
                        "?subject=" + Uri.encode(getString(R.string.app_name)) +
                        "&body=" + Uri.encode("");
        Uri uri = Uri.parse(uriText);
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(uri);

        try {
            startActivity(Intent.createChooser(sendIntent, getString(R.string.about_contact_via)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), R.string.error_no_email_app, Toast.LENGTH_SHORT).show();
            App.getAppComponent().getUtils().copyToClipboard(getString(R.string.about_email));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setTitle(R.string.action_about_app);

        initToolbar();
        initUI();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void initUI() {
        String version = BuildConfig.VERSION_NAME;
        txtVersion.setText(version);
    }

    private void goToSite(String url) {
        CustomTabsIntent intent = new CustomTabsIntent.Builder()
                .setToolbarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                .build();
        intent.launchUrl(getApplicationContext(), Uri.parse(url));
    }
}
