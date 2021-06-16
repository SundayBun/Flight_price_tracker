package com.example.flight_price_tracker_telegram.bot.refactored.states;


import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.service.HandleInputRef;
import com.example.flight_price_tracker_telegram.bot.refactored.service.ResponseMessageRef;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class CountryTextState extends State{

    public CountryTextState(Context context){
        super(context);
        this.textMessageRequest=true;
        this.queryResponse=false;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessageRef.sendMessage(context,  Emojis.EARTH + " Enter the country. " + Emojis.EARTH +
                "\n (enter at least one letter and send it to see available countries)");
   //     return null;
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setState(this);

        if (HandleInputRef.country(context) != null) {
            context.setState(new CountryButtonState(context));
        } else {
            context.setState(new CountryTextState(context));
        }
    }

    @Override
    public State nextState() {
       return context.getState();
    }
}
