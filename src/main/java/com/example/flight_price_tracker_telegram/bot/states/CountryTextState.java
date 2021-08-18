package com.example.flight_price_tracker_telegram.bot.states;


import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.HandleInput;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.Locale;

public class CountryTextState extends State{

    boolean changeState=false;

    public CountryTextState(Context context){
        super(context);
        this.textMessageRequest=true;
        this.queryResponse=false;
        this.stateName = StateName.COUNTRY_TEXT;
        localeMessageService.setLocale(Locale.forLanguageTag(context.getUserData().getLocale()));
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessage.sendMessage(context, localeMessageService.getMessage("state.countryText",Emojis.EARTH,Emojis.EARTH),null);
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setStateName(stateName);
        if (HandleInput.country(context) != null) {
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
