package com.example.flight_price_tracker_telegram.bot.strategy;

import com.example.flight_price_tracker_telegram.bot.Context;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface IStepStrategy {

    BotApiMethod<?> doStateLogic();

    Context getContext();

}
