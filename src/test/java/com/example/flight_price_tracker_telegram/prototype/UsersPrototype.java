package com.example.flight_price_tracker_telegram.prototype;

import com.example.flight_price_tracker_telegram.bot.refactored.states.StateName;
import com.example.flight_price_tracker_telegram.repository.UserData;
import com.example.flight_price_tracker_telegram.repository.UserFlightData;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class UsersPrototype {
    public static final Long chatId= 1L;
    public static final String id="test_id";
    public static final StateName stateName=StateName.START;
    public static final String country="test_country";
    public static final String currency="test_currency";
    public static final String locale="test_locale";

    public static final String destinationPlace="test_destinationPlace";
    public static final String outboundPartialDate="test_outboundPartialDate";
    public static final String inboundPartialDate="test_inboundPartialDate";
    public static final String originPlace="test_originPlace";
    public static final boolean requestNotNull=false;

    public static UserData aUserData(){
        UserData userData=new UserData(chatId,id);
        return userData;

//       return UserData.builder()
//                .chatId(chatId)
//                .id(id)
////                .stateName(stateName)
////                .country(country)
////                .currency(currency)
////                .locale(locale)
//                .build();
    }

    public static UserFlightData aUserFlightData(){
        UserFlightData userFlightData=new UserFlightData(chatId,id);
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
