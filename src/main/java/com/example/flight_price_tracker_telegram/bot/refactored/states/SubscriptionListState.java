package com.example.flight_price_tracker_telegram.bot.refactored.states;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.service.ResponseMessageRef;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import com.example.flight_price_tracker_telegram.repository.UserSubscriptionDataService;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

public class SubscriptionListState extends State {

    private boolean deleteItem = false;
    private int pageNumber = 0;

    public SubscriptionListState(Context context) {
        super(context);
        this.textMessageRequest = true;
        this.queryResponse = true;
        this.stateName = StateName.SUBSCR_LIST;
    }

    @Override
    public BotApiMethod<?> enter(Context context, List<UserSubscription> userSubscriptionList) {
        if (userSubscriptionList.size() > 0) {

            CallbackQuery callbackQuery = context.getCallbackQuery();

            if (callbackQuery != null && isNumeric(callbackQuery.getData()) && Integer.parseInt(callbackQuery.getData())<userSubscriptionList.size()) {
                pageNumber = Integer.parseInt(callbackQuery.getData());
            }

            return ResponseMessageRef.sendSubscripList(context, userSubscriptionList, pageNumber);
        }
        return ResponseMessageRef.sendMessage(context, "No subscriptions");

    }

    @Override
    public void handleInput(Context context, UserSubscriptionDataService repository) {
        context.getUserData().setStateName(stateName);
        if (context.getInput().matches("/Delete_"+"-?\\d+")) {
            try {
                repository.deleteSubscription(repository.findSubByChatId(context.getUserData().getChatId()).get(Integer.parseInt(context.getInput().substring(8))).getId());
                deleteItem = true;
            } catch (IndexOutOfBoundsException exp){
                deleteItem=false;
            }

        }
    }

    @Override
    public State nextState() {
        if (deleteItem) {
            context.setState(new DeleteSubscriptionState(context));
        } else {
            context.setState(new SubscriptionListState(context));
        }
        return context.getState();
    }

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+");
    }
}
