package com.bacecek.yamblz.data.network.places;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("NullableProblems") // object is constructed by Gson
public class Geometry {

    @SerializedName("location")
    @NonNull
    private Coordinates coordinates;

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
