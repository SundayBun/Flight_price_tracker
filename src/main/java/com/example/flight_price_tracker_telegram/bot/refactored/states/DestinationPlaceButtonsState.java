package com.example.flight_price_tracker_telegram.bot.refactored.states;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.service.HandleInputRef;
import com.example.flight_price_tracker_telegram.bot.refactored.service.ResponseMessageRef;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class DestinationPlaceButtonsState extends State{

    public DestinationPlaceButtonsState(Context context) {
        super(context);
        this.textMessageRequest=false;
        this.queryResponse=true;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessageRef.sendSearchPlaces(context, HandleInputRef.places(context)
                , "Select the arrival place");
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setState(this);
        context.getUserFlightData().setDestinationPlace(context.getCallbackQuery().getData());
    }

    @Override
    public State nextState() {
        context.setState(new OutBoundPartialDatesState(context));
        return context.getState();
    }
}
