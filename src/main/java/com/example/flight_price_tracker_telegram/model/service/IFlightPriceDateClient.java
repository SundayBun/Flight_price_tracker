package com.example.flight_price_tracker_telegram.model.service;


import com.example.flight_price_tracker_telegram.model.browse.BrowseDatesDTO;
import com.example.flight_price_tracker_telegram.model.browse.FlightPricesDTO;
import com.example.flight_price_tracker_telegram.repository.UserData;
import com.example.flight_price_tracker_telegram.repository.UserFlightData;

public interface IFlightPriceDateClient {

    BrowseDatesDTO browseQuotes(UserData userData, UserFlightData userFlightData);

}
