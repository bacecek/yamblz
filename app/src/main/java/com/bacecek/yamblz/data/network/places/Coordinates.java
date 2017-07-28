package com.bacecek.yamblz.data.network.places;

import com.google.gson.annotations.SerializedName;

public class Coordinates {

    @SerializedName("lat")
    private float latitude;

    @SerializedName("lng")
    private float longitude;

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
