package com.example.flight_price_tracker_telegram.repository.entity;

import com.example.flight_price_tracker_telegram.bot.states.StateName;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "UserData")
public class UserData implements Serializable {

    @Id
    private Long chatId;
    private String id;
    private StateName stateName;
    private String country;
    private String currency;
    private String locale;

    public UserData(Long chatId, String id) {
        this.chatId = chatId;
        this.id = id;
    }
}
