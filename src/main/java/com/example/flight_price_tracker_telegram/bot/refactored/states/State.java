package com.example.flight_price_tracker_telegram.bot.refactored.states;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import com.example.flight_price_tracker_telegram.repository.UserSubscriptionDataService;
import lombok.EqualsAndHashCode;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.List;

@EqualsAndHashCode
public abstract class State {

    Context context;
    boolean textMessageRequest;
    boolean queryResponse;

    public State(Context context) {
        this.context = context;
    }

    public boolean isQueryResponse() {
        return queryResponse;
    }
    public boolean isTextMessageRequest() {
        return textMessageRequest;
    }

    public BotApiMethod<?> enter(Context context){
        return null;
    };

    public BotApiMethod<?> enter(Context context,  List<UserSubscription> userSubscriptionList){
        return null;
    };

    public void handleInput(Context context){

    };

    public void handleInput(Context context, UserSubscriptionDataService repository){

    };

    public abstract State nextState();


}
