package com.example.flight_price_tracker_telegram.model.service;


import com.example.flight_price_tracker_telegram.model.browse.FlightPricesDTO;
import com.example.flight_price_tracker_telegram.repository.UserData;
import com.example.flight_price_tracker_telegram.repository.UserFlightData;

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

   // FlightPricesDTO browseQuotes(String country,String currency,String locale,String originPlace,String destinationPlace,String outboundPartialDate);

    /**
     * Browse quotes for current flight based on provided arguments. Round trip ticket.
     *
     * @return {@link FlightPricesDTO} object.
     */

    FlightPricesDTO browseQuotes(UserData userData, UserFlightData userFlightData);

}
