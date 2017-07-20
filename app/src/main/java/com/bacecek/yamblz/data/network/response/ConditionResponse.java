package com.bacecek.yamblz.data.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Denis Buzmakov on 12.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class ConditionResponse {
    @SerializedName("id")
    private int conditionId;
    @SerializedName("icon")
    private String conditionIcon;
    private String description;

    public int getConditionId() {
        return conditionId;
    }

    public String getConditionIcon() {
        return conditionIcon;
    }

    public String getDescription() {
        return description;
    }
}
