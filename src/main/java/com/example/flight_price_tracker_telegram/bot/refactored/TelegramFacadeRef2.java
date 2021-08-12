package com.example.flight_price_tracker_telegram.bot.refactored;

import com.example.flight_price_tracker_telegram.bot.refactored.service.strategy.IStepStrategy;
import com.example.flight_price_tracker_telegram.bot.refactored.service.strategy.NewUserStepStrategy;
import com.example.flight_price_tracker_telegram.bot.refactored.service.strategy.RegisteredUserStepStrategy;

import com.example.flight_price_tracker_telegram.bot.refactored.states.*;

import com.example.flight_price_tracker_telegram.repository.UserData;
import com.example.flight_price_tracker_telegram.repository.UserFlightData;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import com.example.flight_price_tracker_telegram.repository.UserSubscriptionDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class TelegramFacadeRef2 {

    private Context context;
    private IStepStrategy stepStrategy;

    private BotApiMethod<?> outgoingData;

    @Autowired
    private UserSubscriptionDataService repository;
    private UserSubscription userSubscription;


    private CallbackQuery callbackQuery;
    private long chatId;
    private String text;
    private String id;

    private void initializeChatIdCallbackQueryTextId(Update update) {
        log.info("**************************");
        if (!update.hasCallbackQuery()) {
            chatId = update.getMessage().getChatId();
            text = update.getMessage().getText();

            log.info("Update data: chatID:{}, text: {}", chatId, text);

        } else {
            id = update.getCallbackQuery().getId();
            callbackQuery = update.getCallbackQuery();

            log.info("Update data: callbackQueryID:{}, callbackQuery: {}, message: {}", id, callbackQuery.getData(),update.hasMessage());
        }
    }

    public BotApiMethod<?> handleUpdate(Update update) {

        initializeChatIdCallbackQueryTextId(update);
        UserData userData = repository.findByChatIdOrId(id, chatId);
        UserFlightData userFlightData = repository.findByIdOrChatID(id, chatId);
         userSubscription = new UserSubscription(chatId);

        if (userData == null) {

            userData = new UserData(chatId, id);
            userFlightData = new UserFlightData(chatId, id);
            context = new Context(userData, userFlightData, text, callbackQuery);
            stepStrategy=new NewUserStepStrategy(context);

        } else {
            context = new Context(userData, userFlightData, text, callbackQuery);

            stepStrategy=new RegisteredUserStepStrategy(context,update,repository);
        }

        outgoingData= stepStrategy.doStateLogic();

        userData=stepStrategy.getContext().getUserData();
        userFlightData=stepStrategy.getContext().getUserFlightData();
        userData.setStateName(stepStrategy.getContext().getState().getStateName());
        userSubscription=stepStrategy.getContext().getUserSubscription();

        repository.saveUserData(userData);
        repository.saveUserFlightData(userFlightData);
        saveSubscrip(stepStrategy.getContext());

        log.info("Process data: chatID:{}, state: {}", chatId, stepStrategy.getContext().getState().getStateName().toString());
        log.info("UserData: chatID:{}, state: {}, country{}, currency{}, locale {}", userData.getChatId(), userData.getStateName(),userData.getCountry(),userData.getCurrency(),userData.getLocale());
        log.info("**************************");


        return outgoingData;
    }

    private void saveSubscrip(Context context) {
        if (context.getState().equals(new SubscriptionState(context))) {
            repository.saveSubscription(userSubscription);
        }
    }

}

