package com.bacecek.yamblz.data.network.places;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Suggestions response from Google Places.
 */
@SuppressWarnings("NullableProblems") // object is constructed by Gson
public class SuggestionsResponse {

    @SerializedName("status")
    @NonNull
    private PlacesResponseStatus status;

    @SerializedName("predictions")
    @NonNull
    private List<Suggestion> suggestions;

    public PlacesResponseStatus getStatus() {
        return status;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }
}
