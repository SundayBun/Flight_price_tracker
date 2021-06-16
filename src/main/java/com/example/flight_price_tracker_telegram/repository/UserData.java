package com.example.flight_price_tracker_telegram.repository;

import com.example.flight_price_tracker_telegram.bot.refactored.states.State;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "UserData")
public class UserData implements Serializable {

    @Id
    private Long chatId;
    private Integer stateID;
    private String id;

    private State state;

    private String country;
    private String currency;
    private String locale;


    public UserData(Long chatId, Integer stateID,String id) {
        this.chatId = chatId;
        this.stateID = stateID;
        this.id=id;
    }
    public UserData(Long chatId, String id){
        this.chatId = chatId;
        this.id=id;
    }
}
