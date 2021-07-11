package com.example.flight_price_tracker_telegram.bot;

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

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class TelegramFacade {
    private BotState state;
    private BotStateContext context;

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

            log.info("Update data: chatID:{}, text: {}", chatId, text);

        } else {
            id = update.getCallbackQuery().getId();
            callbackQuery = update.getCallbackQuery();

            log.info("Update data: callbackQueryID:{}, callbackQuery: {}", id, callbackQuery.getData());
        }
        UserData userData = repository.findByChatIdOrId(id, chatId);
        UserFlightData userFlightData = repository.findByIdOrChatID(id, chatId);

        if (userData == null) {

            state = BotState.getInitialState();

          //  userData = new UserData(chatId, state.ordinal(), id);
            userFlightData = new UserFlightData(chatId, id);


            context = BotStateContext.of(bot, userData, text, callbackQuery, userFlightData, userSubscription);
            sendMessage = state.enter(context);

            repository.saveUserData(userData);
            repository.saveUserFlightData(userFlightData);

            log.info("Process data: chatID:{}, state: {}", chatId, state);

            return sendMessage;
        } else {
            state = BotState.byId(userData.getStateID());
            mainCommand(update);// checking if query has main menu command

            if (state == BotState.DATA_TRANSFERRED) {
                userSubscription = new UserSubscription(chatId);
            }

            context = BotStateContext.of(bot, userData, text, callbackQuery, userFlightData, userSubscription);
        }

        if(state.isQueryResponse() && !state.isTextMessageRequest() && !update.hasCallbackQuery() ){

            return sendMessage;
        } else if(!state.isQueryResponse() && state.isTextMessageRequest() && !update.hasMessage()) {
            return sendMessage;
        }

        if (state == BotState.SUBSCR_LIST || state == BotState.SUBSCR_LIST_EDIT) {
            state.handleInput(context, repository);
        } else {
            state.handleInput(context);
        }

        state = state.nextState();

        if (state == BotState.SUBSCR_LIST || state == BotState.SUBSCR_LIST_EDIT) {
            sendMessage = state.enter(context, repository.findSubByChatId(chatId));
        } else {
            sendMessage = state.enter(context);
        }


        userData.setStateID(state.ordinal());

        repository.saveUserData(userData);
        repository.saveUserFlightData(userFlightData);
        saveSubscrip(state);

        log.info("Process data: chatID:{}, state: {}", chatId, state);

        return sendMessage;
    }

    public void mainCommand(Update update) {

        Map<String, BotState> commands = new HashMap<>();

        commands.put("Button \"ENG\" has been pressed", BotState.START);
        commands.put("Button \"RUS\" has been pressed", BotState.START);
        commands.put("Button \"Find price\" has been pressed", BotState.DATA_FILLED);
        commands.put("USD", BotState.CURRENCY);
        commands.put("EUR", BotState.CURRENCY);
        commands.put("RUB", BotState.CURRENCY);
        commands.put("Button \"Track it\" has been pressed", BotState.DATA_TRANSFERRED);
        commands.put("Button \"One way\" has been pressed", BotState.INBOUND_PARTIAL_DATE);
      //  commands.put("Delete", BotState.SUBSCR_LIST_EDIT);
        commands.put("Change localisation info", BotState.MAIN_MENU);
        commands.put("New search", BotState.MAIN_MENU);
        commands.put("See your track list", BotState.MAIN_MENU);

        if (update.hasMessage() && commands.containsKey(update.getMessage().getText())) {
            state = commands.get(update.getMessage().getText());
        }

        if (update.hasCallbackQuery() && commands.containsKey(update.getCallbackQuery().getData())) {
            state = commands.get(update.getCallbackQuery().getData());
        }

//        if (update.hasMessage() && update.getMessage().getText().startsWith("Delete")) {
//            state = commands.get("Delete");
//        }

    }

    public void saveSubscrip(BotState state) {
        if (state == BotState.SUBSCRIPT) {
            repository.saveSubscription(userSubscription);
        }
    }


}
