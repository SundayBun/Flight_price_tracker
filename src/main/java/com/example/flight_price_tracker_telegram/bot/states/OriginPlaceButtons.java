package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.HandleInput;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class OriginPlaceButtons extends State{

    public OriginPlaceButtons(Context context) {
        super(context);
        this.textMessageRequest=false;
        this.queryResponse=true;
        this.stateName = StateName.ORIGIN_PLACE_BUTTONS;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessage.sendSearchPlaces(context, HandleInput.places(context)
                , "Select the departure place");
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setStateName(stateName);
        context.getUserFlightData().setOriginPlace(context.getCallbackQuery().getData());
    }

    @Override
    public State nextState() {
        context.setState(new DestinationPlaceTextState(context));
        return context.getState();
    }
}
