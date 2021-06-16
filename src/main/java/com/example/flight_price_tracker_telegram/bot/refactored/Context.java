package com.example.flight_price_tracker_telegram.bot.refactored;

import com.example.flight_price_tracker_telegram.bot.refactored.states.StartState;
import com.example.flight_price_tracker_telegram.bot.refactored.states.State;
import com.example.flight_price_tracker_telegram.repository.UserData;
import com.example.flight_price_tracker_telegram.repository.UserFlightData;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Setter
@Getter
public class Context {
    State state;

    private final UserData userData;
    private final UserFlightData userFlightData;
    private UserSubscription userSubscription;

    private final String input;
    private final CallbackQuery callbackQuery;

    public Context(UserData userData, UserFlightData userFlightData, UserSubscription userSubscription, String input, CallbackQuery callbackQuery) {
        this.state = new StartState(this);
        this.userData = userData;
        this.userFlightData = userFlightData;
        this.userSubscription = userSubscription;
        this.input = input;
        this.callbackQuery = callbackQuery;
    }
    public Context(UserData userData, UserFlightData userFlightData, String input, CallbackQuery callbackQuery) {
        this.state = new StartState(this);
        this.userData = userData;
        this.userFlightData = userFlightData;
        this.input = input;
        this.callbackQuery = callbackQuery;
        this.userSubscription=new UserSubscription();
    }

    @Override
    public String toString() {
        return "State = " +
                state.toString();
    }
}
