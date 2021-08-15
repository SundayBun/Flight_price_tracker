package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.HandleInput;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class CountryButtonState extends State{

    public CountryButtonState(Context context) {
        super(context);
        this.textMessageRequest=false;
        this.queryResponse=true;
        this.stateName = StateName.COUNTRY_BUTTONS;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessage.sendSearchCountry(context, HandleInput.country(context)
                , "Select the country you are based in");
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
