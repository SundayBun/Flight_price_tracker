package com.example.flight_price_tracker_telegram.skyscanner_api.service;


import com.example.flight_price_tracker_telegram.skyscanner_api.dto.browse.FlightPricesDTO;
import com.example.flight_price_tracker_telegram.repository.entity.UserData;
import com.example.flight_price_tracker_telegram.repository.entity.UserFlightData;

public interface IFlightPriceClient {
    /**
     * Browse quotes for current flight based on provided arguments. One-way ticket.
     *
     * @param country the country from
     * @param currency the currency to get price
     * @param locale locale for the response
     * @param originPlace origin place
     * @param destinationPlace destination place
     * @param outboundPartialDate outbound date
     * @return {@link FlightPricesDTO} object.
     */

    /**
     * Browse quotes for current flight based on provided arguments. Round trip ticket.
     *
     * @return {@link FlightPricesDTO} object.
     */

    FlightPricesDTO browseQuotes(UserData userData, UserFlightData userFlightData);

}
