package com.example.flight_price_tracker_telegram.bot.refactored.service.strategy;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.repository.UserSubscriptionDataService;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface IStepStrategy {

    BotApiMethod<?> doStateLogic();
    Context getContext();

}
