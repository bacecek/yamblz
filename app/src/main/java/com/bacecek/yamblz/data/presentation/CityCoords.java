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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final CityCoords that = (CityCoords) o;

        return (that.longitude == this.longitude) && (that.latitude == this.latitude);
    }

    @Override
    public int hashCode() {
        int result = (longitude != +0.0f ? Float.floatToIntBits(longitude) : 0);
        result = 31 * result + (latitude != +0.0f ? Float.floatToIntBits(latitude) : 0);
        return result;
    }
}
