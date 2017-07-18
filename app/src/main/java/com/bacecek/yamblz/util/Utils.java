package com.bacecek.yamblz.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bacecek.yamblz.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Denis Buzmakov on 18.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class Utils {

    private AppResources resources;
    private SystemServiceProvider serviceProvider;

    public Utils(AppResources resources, SystemServiceProvider serviceProvider) {
        this.resources = resources;
        this.serviceProvider = serviceProvider;
    }

    /**
     * convert temperature from Kelvin to chosen units
     * @param temperature - temperature
     * @param units - units
     * @return - converted temperature
     */
    @Nullable
    public String convertTemperature(double temperature, @NonNull String units) {
        String result = null;
        if(units.equals(resources.getString(R.string.metric))) {
            double resultTemperature = temperature - 273.15;
            result = resources.getString(R.string.template_temperature_celsius, resultTemperature);
        } else if(units.equals(resources.getString(R.string.imperial))) {
            double resultTemperature = temperature * 9 / 5 - 459.67;
            result = resources.getString(R.string.template_temperature_fahrenheit, resultTemperature);
        }
        return result;
    }

    /**
     * convert sunrise time from Unix time with pattern
     * @param time - Unix time
     * @param pattern - pattern
     * @return - converted time
     */
    @Nullable
    public String formatUnixTime(long time, String pattern) {
        if(time == 0) return null;
        Date date = new Date(time * 1000);
        SimpleDateFormat convertFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        convertFormat.setTimeZone(TimeZone.getDefault());
        return convertFormat.format(date);
    }

    public boolean isOnline() {
        ConnectivityManager cm = serviceProvider.getConnectivityManager();
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    public void copyToClipboard(String text) {
        ClipboardManager manager = serviceProvider.getClipboardManager();
        ClipData data = ClipData.newPlainText("text", text);
        manager.setPrimaryClip(data);
    }

}
