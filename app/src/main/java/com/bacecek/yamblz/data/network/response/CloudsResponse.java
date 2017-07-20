package com.bacecek.yamblz.data.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Denis Buzmakov on 12.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class CloudsResponse {
    @SerializedName("all")
    private int cloudiness;

    public int getCloudiness() {
        return cloudiness;
    }
}
