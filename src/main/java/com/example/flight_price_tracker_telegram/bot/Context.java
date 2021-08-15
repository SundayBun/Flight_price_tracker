package com.example.flight_price_tracker_telegram.bot;

import com.example.flight_price_tracker_telegram.bot.states.StartState;
import com.example.flight_price_tracker_telegram.bot.states.State;
import com.example.flight_price_tracker_telegram.repository.entity.UserData;
import com.example.flight_price_tracker_telegram.repository.entity.UserFlightData;
import com.example.flight_price_tracker_telegram.repository.entity.UserSubscription;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Getter
@Setter
public class Context {
    State state;

    private UserData userData;
    private UserFlightData userFlightData;
    private UserSubscription userSubscription;

    private String input;
    private CallbackQuery callbackQuery;

//    public Context(UserData userData, UserFlightData userFlightData, UserSubscription userSubscription, String input, CallbackQuery callbackQuery) {
//        this.state = new StartState(this);
//        this.userData = userData;
//        this.userFlightData = userFlightData;
//        this.userSubscription = userSubscription;
//        this.input = input;
//        this.callbackQuery = callbackQuery;
//    }
    public Context(UserData userData, UserFlightData userFlightData, String input, CallbackQuery callbackQuery) {
        this.state = new StartState(this);
        this.userData = userData;
        this.userFlightData = userFlightData;
        this.input = input;
        this.callbackQuery = callbackQuery;
        this.userSubscription=new UserSubscription();
    }

    public Context(){}

    @Override
    public String toString() {
        return "State = " +
                state.toString();
    }
}
