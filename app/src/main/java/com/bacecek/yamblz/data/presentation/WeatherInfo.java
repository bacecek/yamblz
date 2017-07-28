package com.bacecek.yamblz.data.presentation;

/**
 * Created by Denis Buzmakov on 14.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class WeatherInfo {
    private String city;
    private String currentTemperature;
    private int conditionId;
    private String conditionIcon;
    private String description;
    private int humidity;
    private double windSpeed;
    private String sunriseTime;
    private String updateTime;

    public WeatherInfo(String city,
                       String currentTemperature,
                       int conditionId,
                       String conditionIcon,
                       String description,
                       int humidity,
                       double windSpeed,
                       String sunriseTime,
                       String updateTime) {
        this.city = city;
        this.currentTemperature = currentTemperature;
        this.conditionId = conditionId;
        this.conditionIcon = conditionIcon;
        this.description = description;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.sunriseTime = sunriseTime;
        this.updateTime = updateTime;
    }

    public String getCity() {
        return city;
    }

    public String getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(String currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public int getConditionId() {
        return conditionId;
    }

    public String getConditionIcon() {
        return conditionIcon;
    }

    public String getDescription() {
        return description;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getSunriseTime() {
        return sunriseTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }
}
