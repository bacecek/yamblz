package com.bacecek.yamblz.data.network.places;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("NullableProblems") // object is constructed by Gson
public class PlaceResponse {

    @SerializedName("status")
    @NonNull
    private PlacesResponseStatus status;

    @SerializedName("result")
    @NonNull
    private Result result;

    @NonNull
    public PlacesResponseStatus getStatus() {
        return status;
    }

    @NonNull
    public Result getResult() {
        return result;
    }
}
