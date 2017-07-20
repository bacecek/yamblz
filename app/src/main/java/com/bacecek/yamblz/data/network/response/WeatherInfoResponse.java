package com.bacecek.yamblz.data.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Denis Buzmakov on 12.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class WeatherInfoResponse {
    @SerializedName("temp")
    private double currentTemperature;
    private int pressure;
    private int humidity;
    @SerializedName("temp_min")
    private double minTemperature;
    @SerializedName("temp_max")
    private double maxTemperature;

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }
}
