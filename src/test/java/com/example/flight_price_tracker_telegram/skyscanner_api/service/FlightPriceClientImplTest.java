package com.example.flight_price_tracker_telegram.skyscanner_api.service;


import com.example.flight_price_tracker_telegram.prototype.UsersPrototype;
import com.example.flight_price_tracker_telegram.repository.entity.UserData;
import com.example.flight_price_tracker_telegram.repository.entity.UserFlightData;
import com.example.flight_price_tracker_telegram.skyscanner_api.dto.browse.FlightPricesDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class FlightPriceClientImplTest {

    FlightPriceClientImpl flightPriceClient;
    UserData userData;
    UserFlightData userFlightData;
    FlightPricesDTO flightPricesDTO;

    @BeforeEach
    void setUp() {
        userData = UsersPrototype.aUserData();
        userData.setCountry("US");
        userData.setCurrency("USD");
        userData.setLocale("en-US");

        userFlightData = UsersPrototype.aUserFlightData();
        userFlightData.setDestinationPlace("LAX-sky");
        userFlightData.setInboundPartialDate("2021-09-20");
        userFlightData.setOriginPlace("SFO-sky");
        userFlightData.setOutboundPartialDate("2021-09-19");
    }

    @Test
    void browseQuotes() {
        flightPriceClient = new FlightPriceClientImpl();
        flightPricesDTO = flightPriceClient.browseQuotes(userData, userFlightData);
        assertThat(flightPricesDTO).isNotNull();
    }
}