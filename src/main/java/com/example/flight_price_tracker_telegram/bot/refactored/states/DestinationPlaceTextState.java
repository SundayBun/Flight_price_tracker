package com.example.flight_price_tracker_telegram.bot.refactored.states;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.service.HandleInputRef;
import com.example.flight_price_tracker_telegram.bot.refactored.service.ResponseMessageRef;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class DestinationPlaceTextState extends State {

    boolean changeState = false;

    public DestinationPlaceTextState(Context context) {
        super(context);
        this.textMessageRequest = true;
        this.queryResponse = false;
        this.stateName = StateName.DESTINATION_PLACE_TEXT;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessageRef.sendMessage(context,
                "Enter the destination place " + Emojis.MAG_RIGHT +
                        "\n (enter at least one letter and send it to see available places)");
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setStateName(stateName);

        if (HandleInputRef.places(context) != null) {
            changeState = true;
        }
    }

    @Override
    public State nextState() {
        if (changeState) {
            context.setState(new DestinationPlaceButtonsState(context));
        } else {
            context.setState(new DestinationPlaceTextState(context));
        }
        return context.getState();
    }
}
