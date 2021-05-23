package com.example.flight_price_tracker_telegram.repository;

import com.example.flight_price_tracker_telegram.model.browse.BrowseDatesDTO;
import com.example.flight_price_tracker_telegram.model.browse.FlightPricesDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;

@Data
@Document(collection = "UserSubscription")
public class UserSubscription {

    @Id
    @GeneratedValue
    private Long id;

    private Long chatId;
    private UserFlightData userFlightData;
    private UserData userData;

    private FlightPricesDTO skyScannerResponseQuotes;
    private BrowseDatesDTO skyScannerResponseDates;
}
