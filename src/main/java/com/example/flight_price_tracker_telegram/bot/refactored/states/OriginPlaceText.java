package com.example.flight_price_tracker_telegram.bot.refactored.states;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.service.HandleInputRef;
import com.example.flight_price_tracker_telegram.bot.refactored.service.ResponseMessageRef;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class OriginPlaceText extends State{
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public OriginPlaceText(Context context) {
        super(context);
        this.textMessageRequest=true;
        this.queryResponse=false;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessageRef.sendMessage(context,  "Enter the origin place " + Emojis.MAG_RIGHT +
                "\n (enter at least one letter and send it to see available places)");
    }

    @Override
    public void handleInput(Context context) {
        context.getUserFlightData().setChatId(context.getUserData().getChatId());
        context.getUserData().setState(this);

        if (HandleInputRef.places(context) != null) {
            context.setState(new OriginPlaceButtons(context));
        } else {
            context.setState(new OriginPlaceText(context));
        }
    }

    @Override
    public State nextState() {
        return context.getState();
    }
}
