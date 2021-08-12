package com.example.flight_price_tracker_telegram.bot.refactored.statesMethodSequence;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class JustEnterMethod extends StatesMethodSequence {

    public JustEnterMethod(Context context) {
        super(context);
    }

    @Override
    public BotApiMethod<?> getStatesMethodSequence() {
        return context.getState().enter(context);
    }

}
