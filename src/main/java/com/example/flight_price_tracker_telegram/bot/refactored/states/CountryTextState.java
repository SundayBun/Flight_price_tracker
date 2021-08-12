package com.example.flight_price_tracker_telegram.bot.refactored.states;


import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.service.HandleInputRef;
import com.example.flight_price_tracker_telegram.bot.refactored.service.ResponseMessageRef;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class CountryTextState extends State{

    boolean changeState=false;

    public CountryTextState(Context context){
        super(context);
        this.textMessageRequest=true;
        this.queryResponse=false;
        this.stateName = StateName.COUNTRY_TEXT;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessageRef.sendMessage(context,  Emojis.EARTH + " Enter the country you are based in" + Emojis.EARTH +
                "\n (enter at least one letter and send it to see available countries)");
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setStateName(stateName);
        if (HandleInputRef.country(context) != null) {
            changeState=true;
        }

    }

    @Override
    public State nextState() {
        if (changeState) {
            context.setState(new CountryButtonState(context));
        } else {
            context.setState(new CountryTextState(context));
        }
        return context.getState();
    }
}
