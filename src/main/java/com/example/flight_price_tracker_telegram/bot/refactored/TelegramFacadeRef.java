package com.example.flight_price_tracker_telegram.bot.refactored;


import com.example.flight_price_tracker_telegram.bot.FlightPriceTrackerTelegramBot;
import com.example.flight_price_tracker_telegram.bot.refactored.states.*;
import com.example.flight_price_tracker_telegram.bot.validation.IInputValidator;
import com.example.flight_price_tracker_telegram.bot.validation.InputValidatorImpl;
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

public class TelegramFacadeRef {


    private Context context;

    private BotApiMethod<?> sendMessage;

    @Autowired
    private UserSubscriptionDataService repository;
    private UserSubscription userSubscription;
    @Autowired
    private IInputValidator inputValidator;

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
        UserData userData = repository.findByChatIdOrId(id,chatId);
        UserFlightData userFlightData = repository.findByIdOrChatID(id,chatId);

        if (userData == null) {

            userData = new UserData(chatId, id);
            userFlightData = new UserFlightData(chatId, id);

            context = new Context(userData, userFlightData, text, callbackQuery);

            userData.setStateName(context.getState().getStateName());

            sendMessage = context.getState().enter(context);

            repository.saveUserData(userData);
            repository.saveUserFlightData(userFlightData);

            log.info("Process data: chatID:{}, state: {}", chatId, context.getState().toString());

            return sendMessage;
        } else {
            context = new Context(userData, userFlightData, text, callbackQuery);
            context.setState(findState());

            mainCommand(update);// checking if query has main menu command

            if (context.getState().equals(new DataTransferredState(context))) {
                userSubscription = new UserSubscription(chatId);
                context.setUserSubscription(userSubscription);
            }

            //sendMessage = context.getState().enter(context);

            if (!inputValidator.isValidInput(update, context)) {
                return sendMessage = context.getState().enter(context);
            }

            if (isSubscrStates(context)) {
                context.getState().handleInput(context, repository);
            } else {
                context.getState().handleInput(context);
            }
                context.getState().nextState();

            if (isSubscrStates(context)) {
                sendMessage = context.getState().enter(context, repository.findSubByChatId(chatId));
            } else {
                sendMessage = context.getState().enter(context);
            }

             userData.setStateName(context.getState().getStateName());

            repository.saveUserData(userData);
            repository.saveUserFlightData(userFlightData);
            saveSubscrip(context);

            log.info("Process data: chatID:{}, state: {}", chatId, context.getState());

            return sendMessage;
        }
    }
    public void mainCommand(Update update) {

        Map<String, State> commands = new HashMap<>();

        commands.put("Button \"ENG\" has been pressed", new StartState(context));
        commands.put("Button \"RUS\" has been pressed", new StartState(context));
        commands.put("Button \"Find price\" has been pressed", new DataFilledState(context));
        commands.put("USD", new CurrencyState(context));
        commands.put("EUR", new CurrencyState(context));
        commands.put("RUB", new CurrencyState(context));
        commands.put("Button \"Track it\" has been pressed", new DataTransferredState(context));
        commands.put("Button \"One way\" has been pressed", new InboundPartialDateState(context));
        //  commands.put("Delete", BotState.SUBSCR_LIST_EDIT);
        commands.put("Change localisation info", new MainMenuState(context));
        commands.put("New search", new MainMenuState(context));
        commands.put("See your track list", new MainMenuState(context));
        commands.put("/start",new StartState(context));

        if (update.hasMessage() && commands.containsKey(update.getMessage().getText())) {
            context.setState(commands.get(update.getMessage().getText()));
        }

        if (update.hasCallbackQuery() && commands.containsKey(update.getCallbackQuery().getData())) {
            context.setState(commands.get(update.getCallbackQuery().getData()));
        }

    }

    public void saveSubscrip(Context context) {
        if (context.getState().equals(new SubscriptionState(context))) {
            repository.saveSubscription(userSubscription);
        }
    }

//    public boolean isInValidInput(Context context, Update update) {
//
//        return (context.getState().isQueryResponse() && !context.getState().isTextMessageRequest() && !update.hasCallbackQuery())
//                || (!context.getState().isQueryResponse() && context.getState().isTextMessageRequest() && !update.hasMessage());
//    }

    public boolean isSubscrStates(Context context) {
        return context.getState().equals(new SubscriptionListState(context));
    }

    public State findState(){
        switch (context.getUserData().getStateName()){
            case START:new StartState(context);
            case COUNTRY_TEXT: return new CountryTextState(context);
            case COUNTRY_BUTTONS: return new CountryButtonState(context);
            case CURRENCY:return new CurrencyState(context);
            case ORIGIN_PLACE_TEXT:return new OriginPlaceText(context);
            case ORIGIN_PLACE_BUTTONS: return new OriginPlaceButtons(context);
            case DESTINATION_PLACE_TEXT: return new DestinationPlaceTextState(context);
            case DESTINATION_PLACE_BUTTONS:return new DestinationPlaceButtonsState(context);
            case OUTBOUND_PARTIAL_DATE:return new OutBoundPartialDatesState(context);
            case INBOUND_PARTIAL_DATE: return new InboundPartialDateState(context);
            case DATA_FILLED: return new DataFilledState(context);
            case DATA_TRANSFERRED: return new DataTransferredState(context);
            case SUBSCR_LIST:return new SubscriptionListState(context);
            case SUBSCRIPT: return new SubscriptionState(context);
            case MAIN_MENU: return new MainMenuState(context);
            default: return new StartState(context);

        }
    }
}