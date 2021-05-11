package com.example.flight_price_tracker_telegram.model.service;

import com.example.flight_price_tracker_telegram.model.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.model.localisation.CurrencyDTO;

import java.io.IOException;
import java.util.List;

/**
 * Client for SkyScanner localisation.
 */

public interface ILocalisationClient {

    /**
     * Retrieve the market countries that SkyScanner flight search API support. Most suppliers (airlines,
     * travel agents and car hire dealers) set their fares based on the market (or country of purchase).
     * It is therefore necessary to specify the market country in every query.
     *
     * @param locale locale of the response.
     *
     * @return the collection of the {@link CountryDTO} objects.
     *
     * @throws IOException
     */

    List<CountryDTO> retrieveCountries(String locale);

    /**
     * Retrieve the currencies that we ScyScanner flight search API.
     *
     * @return the collection of the {@link CurrencyDTO} objects.
     *
     * @throws IOException
     */

    List<CurrencyDTO> retrieveCurrencies();

}
