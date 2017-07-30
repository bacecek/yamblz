package com.bacecek.yamblz.data.network;

import com.bacecek.yamblz.data.network.places.PlacesResponseStatus;

/**
 * Created by vandrikeev on 30.07.17.
 */
public class PlacesApiError extends RuntimeException {

    private PlacesResponseStatus status;

    public PlacesApiError(PlacesResponseStatus status) {
        this.status = status;
    }

    public PlacesResponseStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return String.format("Bad API response status: %s", status);
    }
}
