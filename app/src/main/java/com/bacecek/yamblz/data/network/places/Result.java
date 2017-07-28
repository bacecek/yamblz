package com.bacecek.yamblz.data.network.places;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("NullableProblems") // object is constructed by Gson
public class Result {

    @SerializedName("name")
    @NonNull
    private String name;

    @SerializedName("geometry")
    @NonNull
    private Geometry geometry;

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public Geometry getGeometry() {
        return geometry;
    }
}
