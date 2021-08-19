package com.example.flight_price_tracker_telegram.bot.statesMethodSequence;

import com.example.flight_price_tracker_telegram.bot.Context;
import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

@Data
public abstract class StatesMethodSequence {

    protected Context context;

    public StatesMethodSequence(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public abstract BotApiMethod<?> getStatesMethodSequence();
}
