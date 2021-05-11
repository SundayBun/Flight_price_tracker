package com.example.flight_price_tracker_telegram.repository;

import com.example.flight_price_tracker_telegram.model.browse.BrowsePlacesDTO;
import com.example.flight_price_tracker_telegram.model.browse.FlightPricesDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Data
@Document(collection = "UserSubscriptionFlightData")
public class UserFlightData {

    @Id
    private Long chatId; //задать там, где будет update
    private String destinationPlace;
    private String outboundPartialDate; //LocalDate
    private String inboundPartialDate; //LocalDate
    private String originPlace;
    private FlightPricesDTO skyScannerResponse;
}
