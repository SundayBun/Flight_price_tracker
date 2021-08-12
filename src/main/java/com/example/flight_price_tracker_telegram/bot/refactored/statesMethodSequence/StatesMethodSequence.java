package com.example.flight_price_tracker_telegram.bot.refactored.statesMethodSequence;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

@Data
public abstract class StatesMethodSequence {

    Context context;

    public StatesMethodSequence(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public abstract BotApiMethod<?> getStatesMethodSequence();
}
