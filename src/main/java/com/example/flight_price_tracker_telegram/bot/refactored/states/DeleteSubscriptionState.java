package com.example.flight_price_tracker_telegram.bot.refactored.states;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.service.ResponseMessageRef;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class DeleteSubscriptionState extends State {
    public DeleteSubscriptionState(Context context) {
        super(context);
        this.textMessageRequest=false;
        this.queryResponse=false;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessageRef.sendSubDeleting(context, "Search result was deleted from track list");
    }

    @Override
    public void handleInput(Context context) {

    }

    @Override
    public State nextState() {
        context.setState(new SubscriptionListState(context));
        return context.getState();
    }
}
