package com.bacecek.yamblz;

import com.bacecek.yamblz.data.network.places.PlaceResponse;
import com.bacecek.yamblz.data.network.places.SuggestionsResponse;
import com.bacecek.yamblz.data.network.response.WeatherResponse;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;

class ResourcesReader {

    private Gson gson;

    ResourcesReader() {
        this.gson = new Gson();
    }

    private InputStreamReader readTestResource(String fileName) throws FileNotFoundException {
        return new InputStreamReader(this.getClass().getResourceAsStream(fileName));
    }

    SuggestionsResponse getSuggestionsMoscowOK() throws FileNotFoundException {
        return gson.fromJson(readTestResource("places_api_suggestions_Moscow_OK.json"), SuggestionsResponse.class);
    }

    SuggestionsResponse getSuggestionsMoscowNoApiKey() throws FileNotFoundException {
        return gson.fromJson(readTestResource("places_api_suggestions_Moscow_NoApiKey.json"), SuggestionsResponse.class);
    }

    PlaceResponse getDetailsMoscowOK() throws FileNotFoundException {
        return gson.fromJson(readTestResource("places_api_details_Moscow_OK.json"), PlaceResponse.class);
    }

    PlaceResponse getDetailsMoscowNoApiKey() throws FileNotFoundException {
        return gson.fromJson(readTestResource("places_api_details_Moscow_NoApiKey.json"), PlaceResponse.class);
    }

    WeatherResponse getWeatherMoscowOK() throws FileNotFoundException {
        return gson.fromJson(readTestResource("weather_api_Moscow_OK.json"), WeatherResponse.class);
    }

}
