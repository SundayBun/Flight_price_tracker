package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.HandleInput;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.Locale;

public class CountryButtonState extends State{

    public CountryButtonState(Context context) {
        super(context);
        this.textMessageRequest=false;
        this.queryResponse=true;
        this.stateName = StateName.COUNTRY_BUTTONS;
        localeMessageService.setLocale(Locale.forLanguageTag(context.getUserData().getLocale()));
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessage.sendSearchCountry(context, HandleInput.country(context)
                , localeMessageService.getMessage("state.countryButton"));
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setStateName(stateName);
        context.getUserData().setCountry(context.getCallbackQuery().getData());
    }

    @Override
    public State nextState() {
        context.setState(new CurrencyState(context));
        return context.getState();
    }
}
