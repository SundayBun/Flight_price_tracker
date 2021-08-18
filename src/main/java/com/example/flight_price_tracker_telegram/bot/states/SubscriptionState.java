package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.Locale;

public class SubscriptionState extends State {

    public SubscriptionState(Context context) {
        super(context);
        this.textMessageRequest=false;
        this.queryResponse=false;
        this.stateName = StateName.SUBSCRIPT;
        localeMessageService.setLocale(Locale.forLanguageTag(context.getUserData().getLocale()));
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessage.sendSubConfirmation(context, localeMessageService.getMessage("state.subscription"));
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
