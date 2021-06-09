package com.example.flight_price_tracker_telegram.bot;

import com.example.flight_price_tracker_telegram.repository.UserData;
import com.example.flight_price_tracker_telegram.repository.UserFlightData;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Getter
public class BotStateContext {

    private final FlightPriceTrackerTelegramBot bot;
    private final UserData userData;
    private final UserFlightData userFlightData;
    private final UserSubscription userSubscription;

    private final String input;
    private final CallbackQuery callbackQuery;



    public BotStateContext(FlightPriceTrackerTelegramBot bot, UserData userData, String input, CallbackQuery callbackQuery, UserFlightData userFlightData, UserSubscription userSubscription) {
        this.userFlightData = userFlightData;
        this.bot = bot;
        this.userData = userData;
        this.input = input;
        this.callbackQuery = callbackQuery;
        this.userSubscription=userSubscription;
    }

    public static BotStateContext of(FlightPriceTrackerTelegramBot bot, UserData userData, String text, CallbackQuery callbackQuery, UserFlightData userFlightData, UserSubscription userSubscription) {
        return new BotStateContext(bot,userData,text,callbackQuery,userFlightData,userSubscription) ;
    }
}
