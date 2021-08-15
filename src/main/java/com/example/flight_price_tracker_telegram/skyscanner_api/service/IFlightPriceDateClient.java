package com.example.flight_price_tracker_telegram.skyscanner_api.service;


import com.example.flight_price_tracker_telegram.skyscanner_api.dto.browse.BrowseDatesDTO;
import com.example.flight_price_tracker_telegram.repository.entity.UserData;
import com.example.flight_price_tracker_telegram.repository.entity.UserFlightData;

public interface IFlightPriceDateClient {

    BrowseDatesDTO browseQuotes(UserData userData, UserFlightData userFlightData);

}
