package com.example.flight_price_tracker_telegram.prototype;

import com.example.flight_price_tracker_telegram.bot.states.StateName;
import com.example.flight_price_tracker_telegram.repository.entity.UserData;
import com.example.flight_price_tracker_telegram.repository.entity.UserFlightData;


public class UsersPrototype {
    public static final Long chatId = 1L;
    public static final String id = "test_id";

    public static UserData aUserData() {
        UserData userData = new UserData(chatId, id);
        userData.setStateName(StateName.START);
        return userData;
    }

    public static UserFlightData aUserFlightData() {
        UserFlightData userFlightData = new UserFlightData(chatId, id);
        return userFlightData;
//        return UserFlightData.builder()
//                .chatId(chatId)
//                .id(id)
//                .destinationPlace(destinationPlace)
//                .outboundPartialDate(outboundPartialDate)
//                .inboundPartialDate(inboundPartialDate)
//                .originPlace(originPlace)
//                .requestNotNull(requestNotNull)
//                .build();
    }
}
