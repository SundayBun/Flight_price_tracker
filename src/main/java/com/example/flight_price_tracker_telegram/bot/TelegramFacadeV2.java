package com.example.flight_price_tracker_telegram.bot;

import com.example.flight_price_tracker_telegram.bot.service.ButtonHandlerV2;
import com.example.flight_price_tracker_telegram.repository.UserData;
import com.example.flight_price_tracker_telegram.repository.UserFlightData;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import com.example.flight_price_tracker_telegram.repository.UserSubscriptionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramFacadeV2 {
    private BotState state;
    private BotStateContextRepo context;

    private BotApiMethod<?> sendMessage;

    @Autowired
    private UserSubscriptionDataService repository;
    private UserSubscription userSubscription;

    private CallbackQuery callbackQuery;
    private long chatId;
    private String text;
    private String id;

    public BotApiMethod<?> handleUpdate(Update update, FlightPriceTrackerTelegramBot bot) {

        if (!update.hasCallbackQuery()) {
            chatId = update.getMessage().getChatId();
            text = update.getMessage().getText();
        } else {
            id = update.getCallbackQuery().getId();
            callbackQuery = update.getCallbackQuery();

        }
        UserData userData = repository.findByIdOrChatId(id, chatId);
        UserFlightData userFlightData = repository.findByIdOrChatID(id, chatId);

        if (userData == null) {
            state = BotState.getInitialState();

            userData = new UserData(chatId, state.ordinal(), id);
            userFlightData = new UserFlightData(chatId, id);


            context = BotStateContextRepo.of(bot, userData, text, callbackQuery, userFlightData,userSubscription);
            sendMessage = state.enter(context);

            repository.saveUserData(userData);
            repository.saveUserFlightData(userFlightData);

            return sendMessage;
        } else {
            state = BotState.byId(userData.getStateID());
            mainMenuCommand(update);// checking if query has main menu command

            if(state==BotState.DATA_TRANSFERRED) {
                userSubscription=new UserSubscription(chatId);
            }

            context = BotStateContextRepo.of(bot, userData, text, callbackQuery, userFlightData,userSubscription);
        }


        state.handleInput(context);

        state = state.nextState();

        if(state==BotState.SUBSCR_LIST){
            sendMessage=enterSubscrip(context);
        }else
        {sendMessage = state.enter(context);}


        userData.setStateID(state.ordinal());

        repository.saveUserData(userData);
        repository.saveUserFlightData(userFlightData);
        saveSubscrip(state);

        return sendMessage;
    }

    public void mainMenuCommand(Update update) {

        List<String> mainMenu = new ArrayList<>();
        mainMenu.add("Change localisation info");
        mainMenu.add("New search");
        mainMenu.add("See your track list");

        if (update.hasMessage() && mainMenu.contains(update.getMessage().getText())) {
            state = BotState.MAIN_MENU;

        }
    }

    public void saveSubscrip(BotState state){
        if (state==BotState.SUBSCRIPT) {
            repository.saveSubscription(userSubscription);
        }
    }

    public SendMessage enterSubscrip(BotStateContextRepo context){
        List<UserSubscription> subscription=repository.findSubByChatId(chatId);
        SendMessage message = new SendMessage();
        message.setChatId(context.getUserData().getChatId().toString());
        message.setReplyMarkup(ButtonHandlerV2.getMessageFromKeyboardSubList(subscription));
        message.setText("Flight list");
        return message;
    }


}
