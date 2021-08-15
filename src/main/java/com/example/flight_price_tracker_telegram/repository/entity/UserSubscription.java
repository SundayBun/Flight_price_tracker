package com.example.flight_price_tracker_telegram.repository.entity;

import com.example.flight_price_tracker_telegram.skyscanner_api.dto.browse.BrowseDatesDTO;
import com.example.flight_price_tracker_telegram.skyscanner_api.dto.browse.FlightPricesDTO;
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
    private Integer minPrice;
    private UserFlightData userFlightData;
    private UserData userData;

    private FlightPricesDTO skyScannerResponseQuotes;
    private BrowseDatesDTO skyScannerResponseDates;

    public UserSubscription (Long chatId){
        this.chatId=chatId;
    }
    public UserSubscription (){
    }
}
