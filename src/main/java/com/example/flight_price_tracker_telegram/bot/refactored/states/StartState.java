package com.example.flight_price_tracker_telegram.bot.refactored.states;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.service.ResponseMessageRef;
import lombok.EqualsAndHashCode;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;


public class StartState extends State{

    public StartState(Context context){
        super(context);
        this.textMessageRequest=true;
        this.queryResponse=true;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessageRef.sendMessage(context,  "Choose the language");
    }

    @Override
    public void handleInput(Context context) {
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
