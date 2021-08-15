package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.HandleInput;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class DestinationPlaceButtonsState extends State{

    public DestinationPlaceButtonsState(Context context) {
        super(context);
        this.textMessageRequest=false;
        this.queryResponse=true;
        this.stateName = StateName.DESTINATION_PLACE_BUTTONS;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessage.sendSearchPlaces(context, HandleInput.places(context)
                , "Select the arrival place");
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setStateName(stateName);
        context.getUserFlightData().setDestinationPlace(context.getCallbackQuery().getData());
    }

    @Override
    public State nextState() {
        context.setState(new OutBoundPartialDatesState(context));
        return context.getState();
    }
}
