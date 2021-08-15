package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class DeleteSubscriptionState extends State {
    public DeleteSubscriptionState(Context context) {
        super(context);
        this.textMessageRequest=false;
        this.queryResponse=false;
        this.stateName = StateName.DELETE_SUBSCR;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessage.sendSubDeleting(context, "Search result was deleted from track list");
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
