package com.bacecek.yamblz.data.network.places;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Suggestion item.
 */
@SuppressWarnings("NullableProblems") // object is constructed by Gson
public class Suggestion {

    @SerializedName("description")
    @NonNull
    private String description;

    @SerializedName("place_id")
    @NonNull
    private String placeId;

    public String getDescription() {
        return description;
    }

    public String getPlaceId() {
        return placeId;
    }
}
