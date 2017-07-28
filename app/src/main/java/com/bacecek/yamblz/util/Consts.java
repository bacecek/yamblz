package com.bacecek.yamblz.util;

/**
 * Created by Denis Buzmakov on 12.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class Consts {
    public static final String TAG_WEATHER_SERVICE = "weather_service";
    public static final int DEFAULT_UPDATE_INTERVAL = 3600;
    public static final int UPDATE_INTERVAL_WINDOW = 300;

    public static class Prefs {
        public static final String KEY_LAST_WEATHER = "last_weather";
        public static final String KEY_TEMP_UNITS = "temp_units";
        public static final String KEY_UPDATE_INTERVAL = "update_interval";
        public static final String KEY_CITY_NAME = "city_name";
        public static final String KEY_CITY_ID = "city_id";
        public static final String KEY_CITY_LON = "city_lon";
        public static final String KEY_CITY_LAT = "city_lat";
    }
}
