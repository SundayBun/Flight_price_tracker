package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;


public class StartState extends State{

    public StartState(Context context){
        super(context);
        this.textMessageRequest=false;
        this.queryResponse=true;
        this.stateName = StateName.START;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessage.sendMessage(context,  "Choose the language");
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setStateName(stateName);
        if (context.getCallbackQuery().getData().equals("Button \"ENG\" has been pressed")) {
            context.getUserData().setLocale("en-US");
        } else {
            context.getUserData().setLocale("ru-RU");
        }
    }

    @Override
    public State nextState() {
        context.setState(new CountryTextState(context));
        return context.getState();
    }
}
