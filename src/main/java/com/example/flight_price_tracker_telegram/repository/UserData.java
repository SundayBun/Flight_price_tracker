package com.example.flight_price_tracker_telegram.repository;

import com.example.flight_price_tracker_telegram.model.browse.BrowsePlacesDTO;
import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
@Document(collection = "UserSubscriptionData")
public class UserData implements Serializable {

    @Id
    private Long chatId; //задать там, где будет update
    private Integer stateID;
    private String id;

    private String country;
    private String currency;
    private String locale;


    public UserData(Long chatId, Integer stateID,String id) {
        this.chatId = chatId;
        this.stateID = stateID;
        this.id=id;
    }
}
