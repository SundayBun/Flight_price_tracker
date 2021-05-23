package com.example.flight_price_tracker_telegram.bot;

import com.example.flight_price_tracker_telegram.repository.UserData;
import com.example.flight_price_tracker_telegram.repository.UserFlightData;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import com.example.flight_price_tracker_telegram.repository.UserSubscriptionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
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
            context = BotStateContextRepo.of(bot, userData, text, callbackQuery, userFlightData);
            sendMessage = state.enter(context);

            repository.saveUserData(userData);
            repository.saveUserFlightData(userFlightData);

            return sendMessage;
        } else {
            state = BotState.byId(userData.getStateID());
            mainMenuCommand(update);// checking if query has main menu command
            context = BotStateContextRepo.of(bot, userData, text, callbackQuery, userFlightData);
        }
        handleInputOption(state,context);

        state = state.nextState();
        sendMessage = state.enter(context);
        userData.setStateID(state.ordinal());
        repository.saveUserData(userData);
        repository.saveUserFlightData(userFlightData);

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

    public void handleInputOption(BotState state, BotStateContextRepo context){
        if (state==BotState.SUBSCRIPT) {
            userSubscription=new UserSubscription();
            state.handleInput(context,userSubscription);
            repository.saveSubscription(userSubscription);
        }
        else state.handleInput(context);
    }
}
