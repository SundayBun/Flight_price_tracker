package com.example.flight_price_tracker_telegram.bot.refactored.states;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.service.ResponseMessageRef;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import com.example.flight_price_tracker_telegram.repository.UserSubscriptionDataService;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.List;

public class SubscriptionListState extends State {

    public SubscriptionListState(Context context) {
        super(context);
        this.textMessageRequest=true;
        this.queryResponse=true;
    }

    @Override
    public BotApiMethod<?> enter(Context context, List<UserSubscription> userSubscriptionList) {
        if (userSubscriptionList.size() > 0) {
            return ResponseMessageRef.sendSubscripList(context, userSubscriptionList);
        }
        return ResponseMessageRef.sendMessage(context,  "No subscriptions");
    }

    @Override
    public void handleInput(Context context, UserSubscriptionDataService repository) {

        if (context.getInput().startsWith("/Delete_")) {
            repository.deleteSubscription(repository.findSubByChatId(context.getUserData().getChatId()).get(Integer.parseInt(context.getInput().substring(8))).getId());
            context.setState(new DeleteSubscriptionState(context));
        } else {
            context.setState(new SubscriptionListEditState(context));
        }
    }

    @Override
    public State nextState() {
        return context.getState();
    }
}
