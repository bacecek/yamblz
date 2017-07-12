package com.bacecek.yamblz.data.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Denis Buzmakov on 12.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class WindResponse {
    private double speed;
    @SerializedName("deg")
    private double direction;

    public double getSpeed() {
        return speed;
    }

    public double getDirection() {
        return direction;
    }
}
