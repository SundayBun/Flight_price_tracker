package com.example.flight_price_tracker_telegram.bot.refactored.states;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.service.HandleInputRef;
import com.example.flight_price_tracker_telegram.bot.refactored.service.ResponseMessageRef;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class DestinationPlaceTextState extends State{

    public DestinationPlaceTextState(Context context) {
        super(context);
        this.textMessageRequest=true;
        this.queryResponse=false;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessageRef.sendMessage(context,
                "Enter the destination place " + Emojis.MAG_RIGHT +
                        "\n (enter at least one letter and send it to see available places)");
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setState(this);

        if (HandleInputRef.places(context) != null) {
            context.setState(new DestinationPlaceButtonsState(context));
        } else {
            context.setState(new DestinationPlaceTextState(context));
        }
    }

    @Override
    public State nextState() {
        return context.getState();
    }
}
