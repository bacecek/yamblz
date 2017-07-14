package com.bacecek.yamblz.data.presentation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Denis Buzmakov on 14.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class WeatherInfo implements Parcelable {
    private double currentTemperature;
    private int conditionId;
    private int humidity;
    private double windSpeed;
    private long sunriseTime;

    public WeatherInfo(double currentTemperature, int conditionId, int humidity, double windSpeed, long sunriseTime) {
        this.currentTemperature = currentTemperature;
        this.conditionId = conditionId;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.sunriseTime = sunriseTime;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public int getConditionId() {
        return conditionId;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public long getSunriseTime() {
        return sunriseTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.currentTemperature);
        dest.writeInt(this.conditionId);
        dest.writeInt(this.humidity);
        dest.writeDouble(this.windSpeed);
        dest.writeLong(this.sunriseTime);
    }

    protected WeatherInfo(Parcel in) {
        this.currentTemperature = in.readDouble();
        this.conditionId = in.readInt();
        this.humidity = in.readInt();
        this.windSpeed = in.readDouble();
        this.sunriseTime = in.readLong();
    }

    public static final Creator<WeatherInfo> CREATOR = new Creator<WeatherInfo>() {
        @Override
        public WeatherInfo createFromParcel(Parcel source) {
            return new WeatherInfo(source);
        }

        @Override
        public WeatherInfo[] newArray(int size) {
            return new WeatherInfo[size];
        }
    };
}
