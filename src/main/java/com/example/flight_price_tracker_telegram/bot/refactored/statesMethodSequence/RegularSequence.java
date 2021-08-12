package com.example.flight_price_tracker_telegram.bot.refactored.statesMethodSequence;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class RegularSequence extends StatesMethodSequence {

    public RegularSequence(Context context) {
        super(context);
    }

    @Override
    public BotApiMethod<?> getStatesMethodSequence(){
        context.getState().handleInput(context);
        context.getState().nextState();
        return context.getState().enter(context);
    }
}

