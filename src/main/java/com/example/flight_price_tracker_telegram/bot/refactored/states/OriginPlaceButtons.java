package com.example.flight_price_tracker_telegram.bot.refactored.states;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.service.HandleInputRef;
import com.example.flight_price_tracker_telegram.bot.refactored.service.ResponseMessageRef;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class OriginPlaceButtons extends State{

    public OriginPlaceButtons(Context context) {
        super(context);
        this.textMessageRequest=false;
        this.queryResponse=true;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessageRef.sendSearchPlaces(context, HandleInputRef.places(context)
                , "Select the departure place");
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setState(this);
        context.getUserFlightData().setOriginPlace(context.getCallbackQuery().getData());
    }

    @Override
    public State nextState() {
        context.setState(new DestinationPlaceTextState(context));
        return context.getState();
    }
}
