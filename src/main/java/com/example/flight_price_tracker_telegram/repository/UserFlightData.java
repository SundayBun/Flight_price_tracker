package com.example.flight_price_tracker_telegram.repository;

import com.example.flight_price_tracker_telegram.model.browse.BrowsePlacesDTO;
import com.example.flight_price_tracker_telegram.model.browse.FlightPricesDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


@Data
@Document(collection = "UserSubscriptionFlightData")
public class UserFlightData {

    public UserFlightData(Long chatId, String id) {
        this.chatId = chatId;
        this.id=id;
    }
    public UserFlightData(){}

    @Id
    private Long chatId; //задать там, где будет update
    private String id;
    private String destinationPlace;
    private String outboundPartialDate; //LocalDate
    private String inboundPartialDate; //LocalDate
    private String originPlace;
    private FlightPricesDTO skyScannerResponse;


}
