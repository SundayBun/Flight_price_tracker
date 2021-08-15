package com.example.flight_price_tracker_telegram.repository.entity;

import com.example.flight_price_tracker_telegram.skyscanner_api.dto.browse.BrowseDatesDTO;
import com.example.flight_price_tracker_telegram.skyscanner_api.dto.browse.FlightPricesDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;


@Data
@Document(collection = "UserFlightData")
public class UserFlightData implements Serializable {

    public UserFlightData(Long chatId, String id) {
        this.chatId = chatId;
        this.id=id;
    }
    public UserFlightData(){}

    @Id
    private Long chatId;
    private String id;

    private String destinationPlace;
    private String outboundPartialDate;
    private String inboundPartialDate;
    private String originPlace;
    private boolean requestNotNull;
    private FlightPricesDTO skyScannerResponseQuotes;
    private BrowseDatesDTO skyScannerResponseDates;


    protected boolean canEqual(final Object other) {
        return other instanceof UserFlightData;
    }

}
