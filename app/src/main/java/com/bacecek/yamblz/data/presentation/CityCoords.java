package com.bacecek.yamblz.data.presentation;

import android.support.annotation.NonNull;

/**
 * Created by vandrikeev on 27.07.17.
 */
public class CityCoords {

    private float longitude;

    private float latitude;

    private CityCoords() {
    }

    public static class Builder {

        @NonNull
        private CityCoords cityCoords;

        public Builder() {
            this.cityCoords = new CityCoords();
        }

        public CityCoords.Builder setLongitude(float longitude) {
            cityCoords.longitude = longitude;
            return this;
        }

        public CityCoords.Builder setLatitude(float latitude) {
            cityCoords.latitude = latitude;
            return this;
        }

        public CityCoords build() {
            return cityCoords;
        }
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }
}
