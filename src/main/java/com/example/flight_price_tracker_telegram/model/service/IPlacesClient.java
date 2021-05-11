package com.example.flight_price_tracker_telegram.model.service;

import com.example.flight_price_tracker_telegram.model.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.model.places.PlacesDTO;

import java.util.List;

public interface IPlacesClient {

    /**
     * Get a list of places that match a query string based on arguments.
     *
     * @param query the code of the city.
     * @param country the code of the country.
     * @param currency the code of the currency.
     * @param locale the code of the locale.
     * @return the collection of the {@link PlacesDTO} objects.
     */

    List<PlacesDTO> retrievePlaces(String query, String country, String currency, String locale);

}
