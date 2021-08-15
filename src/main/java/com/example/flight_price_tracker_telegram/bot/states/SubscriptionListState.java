package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import com.example.flight_price_tracker_telegram.repository.entity.UserSubscription;
import com.example.flight_price_tracker_telegram.repository.UserSubscriptionDataService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Slf4j
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

        log.info("deleteItem: {}",deleteItem);

        if(deleteItem) {
            return getDeleteItemMessage(context);}

        if (userSubscriptionList.size() > 0) {

            CallbackQuery callbackQuery = context.getCallbackQuery();

            if (callbackQuery != null && isNumeric(callbackQuery.getData()) && Integer.parseInt(callbackQuery.getData())<userSubscriptionList.size()) {
                pageNumber = Integer.parseInt(callbackQuery.getData());
            }

            return ResponseMessage.sendSubscripList(context, userSubscriptionList, pageNumber);
        }

        return ResponseMessage.sendMessage(context, "No subscriptions");

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
            log.info("deleteItem: {}",deleteItem);
        }
    }

    @Override
    public State nextState() {
//        if (deleteItem) {
//            context.setState(new DeleteSubscriptionState(context));
//        } else {
//            context.setState(new SubscriptionListState(context));
//        }
//        return context.getState();
        return null;
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+");
    }

    private BotApiMethod<?> getDeleteItemMessage(Context context){
          return ResponseMessage.sendSubDeleting(context, "Search result was deleted from track list");
    }
}
