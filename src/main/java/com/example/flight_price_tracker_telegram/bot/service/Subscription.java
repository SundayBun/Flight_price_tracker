package com.example.flight_price_tracker_telegram.bot.service;

import com.example.flight_price_tracker_telegram.bot.BotStateContextRepo;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import com.example.flight_price_tracker_telegram.repository.UserSubscriptionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class Subscription {
    @Autowired
    private UserSubscriptionDataService repository;

    public List<UserSubscription> subscriptions(BotStateContextRepo context){

      Long chatId=context.getUserData().getChatId();

        List<UserSubscription> subscription=repository.findSubByChatId(chatId);
        return subscription;

    }


}
