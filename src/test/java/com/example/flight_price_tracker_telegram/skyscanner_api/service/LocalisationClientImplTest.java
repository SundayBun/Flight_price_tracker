package com.example.flight_price_tracker_telegram.skyscanner_api.service;

import com.example.flight_price_tracker_telegram.skyscanner_api.dto.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.skyscanner_api.dto.localisation.CurrencyDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocalisationClientImplTest {

    String locale;
    LocalisationClientImpl localisationClient;

    @BeforeEach
    void setUp() {
        locale="en-US";
        localisationClient=new LocalisationClientImpl();
    }

    @Test
    void retrieveCountries() {
        List<CountryDTO> countryDTOList=localisationClient.retrieveCountries(locale);
        assertThat(countryDTOList).isNotNull();
    }

    @Test
    void retrieveCurrencies() {
        List<CurrencyDTO> currencyDTOList=localisationClient.retrieveCurrencies();
        assertThat(currencyDTOList).isNotNull();
    }
}