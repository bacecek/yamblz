package com.bacecek.yamblz.data.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Denis Buzmakov on 12.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class WeatherResponse {

    @SerializedName("weather")
    private List<ConditionResponse> conditions;

    @SerializedName("main")
    private WeatherInfoResponse info;

    @SerializedName("clouds")
    private CloudsResponse cloudsInfo;

    @SerializedName("sys")
    private InternalInfoResponse internalInfo;

    private int visibility;

    @SerializedName("wind")
    private WindResponse windInfo;

    @SerializedName("dt")
    private long updateTime;

    public List<ConditionResponse> getConditions() {
        return conditions;
    }

    public WeatherInfoResponse getInfo() {
        return info;
    }

    public int getVisibility() {
        return visibility;
    }

    public WindResponse getWindInfo() {
        return windInfo;
    }

    public CloudsResponse getCloudsInfo() {
        return cloudsInfo;
    }

    public InternalInfoResponse getInternalInfo() {
        return internalInfo;
    }

    public long getUpdateTime() {
        return updateTime;
    }
}
