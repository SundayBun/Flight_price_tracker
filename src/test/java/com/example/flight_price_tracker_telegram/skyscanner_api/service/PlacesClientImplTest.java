package com.example.flight_price_tracker_telegram.skyscanner_api.service;


import com.example.flight_price_tracker_telegram.skyscanner_api.dto.places.PlacesDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


class PlacesClientImplTest {
    PlacesClientImpl placesClient;

    String query = "Mos";
    String country = "RU";
    String currency = "USD";
    String locale = "en-US";

    @Test
    void retrievePlaces() {
        placesClient = new PlacesClientImpl();
        List<PlacesDTO> placesDTOList = placesClient.retrievePlaces(query, country, currency, locale);
        assertThat(placesDTOList).isNotNull();
    }
}