package com.example.flight_price_tracker_telegram.bot.statesMethodSequence;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.repository.UserSubscriptionDataService;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class SequenceWithRepo extends StatesMethodSequence {

    UserSubscriptionDataService repository;

    public SequenceWithRepo(Context context, UserSubscriptionDataService repository) {
        super(context);
        this.repository = repository;
    }

    @Override
    public BotApiMethod<?> getStatesMethodSequence() {
        try {
            context.getState().handleInput(context, repository);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        // context.getState().nextState();
        return context.getState().enter(context, repository.findSubByChatId(context.getUserData().getChatId()));
    }
}
