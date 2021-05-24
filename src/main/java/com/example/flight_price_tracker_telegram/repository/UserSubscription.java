package com.example.flight_price_tracker_telegram.repository;

import com.example.flight_price_tracker_telegram.model.browse.BrowseDatesDTO;
import com.example.flight_price_tracker_telegram.model.browse.FlightPricesDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import java.io.Serializable;

@Data
@Document(collection = "UserSubscription")
public class UserSubscription implements Serializable {

    @Id
    @GeneratedValue
    private String id;

    private Long chatId;
    private UserFlightData userFlightData;
    private UserData userData;

    private FlightPricesDTO skyScannerResponseQuotes;
    private BrowseDatesDTO skyScannerResponseDates;

    public UserSubscription (Long chatId){
        this.chatId=chatId;
    }
}
