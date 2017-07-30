package com.bacecek.yamblz.data.presentation;

import android.support.annotation.NonNull;

/**
 * Created by vandrikeev on 22.07.17.
 */
public class City {

    @NonNull
    private String name;

    @NonNull
    private String id;

    public City(@NonNull String name, @NonNull String id) {
        this.name = name;
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final City that = (City) o;

        return this.name.equals(that.name) && this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
