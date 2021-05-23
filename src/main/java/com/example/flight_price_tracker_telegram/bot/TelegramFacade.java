package com.example.flight_price_tracker_telegram.bot;

import com.example.flight_price_tracker_telegram.model.browse.FlightPricesDTO;
import com.example.flight_price_tracker_telegram.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramFacade {

    private BotState state;
    private BotStateContextRepo context;

    private BotApiMethod sendMessage;

    @Autowired
    private UserSubscriptionDataService repository;

    private CallbackQuery callbackQuery;
    private long chatId;
    private String text;

    public BotApiMethod<Message> handleUpdate(Update update, FlightPriceTrackerTelegramBot bot) {

     //   chatId = update.getMessage().getChatId();

        if (update.hasCallbackQuery()) {
            callbackQuery = update.getCallbackQuery();
            chatId =callbackQuery.getMessage().getChatId();
        } else {
            chatId = update.getMessage().getChatId();
            text = update.getMessage().getText();
        }

        UserData userData = repository.findByChatId(chatId);
        UserFlightData userFlightData=repository.findByChatID(chatId);;

        if (userData==null) {
            state = BotState.getInitialState();
         //   userData = new UserData(chatId, state.ordinal());
          //  userFlightData = new UserFlightData(chatId);
            context = BotStateContextRepo.of(bot, userData, text, callbackQuery, userFlightData);
            sendMessage = state.enter(context);

           // state.handleInput(context);
          //  state = state.nextState();

           // userData.setStateID(state.ordinal());
            repository.saveUserData(userData);
          //  repository.saveUserFlightData(userFlightData);

            return sendMessage;
        } else {

            context = BotStateContextRepo.of(bot, userData, text, callbackQuery, userFlightData);
            state = BotState.byId(userData.getStateID());
            //state = state.nextState();

        }

       // sendMessage = state.enter(context);

        state.handleInput(context);
        state = state.nextState();
        sendMessage = state.enter(context);
        userData.setStateID(state.ordinal());
        repository.saveUserData(userData);

//        if (userData == null) {
//            newUser(userData,bot,null);
//            return sendMessage;
//        } else {
//            notNewUser(userData,bot,userFlightData);
//        }
//
//        state.handleInput(context);



//        do {
//            state = state.nextState();
//            sendMessage = state.enter(context);
//        } while (!state.isInputNeeded());

       // userData.setStateID(state.ordinal());


           // repository.saveUserFlightData(context.getUserFlightData());


        return sendMessage;
    }

    public void newUser(UserData userData, FlightPriceTrackerTelegramBot bot,UserFlightData userFlightData) {
        state = BotState.getInitialState();
       // userData = new UserData(chatId, state.ordinal());
        repository.saveUserData(userData);
        context = BotStateContextRepo.of(bot, userData, text, callbackQuery, userFlightData);
        sendMessage = state.enter(context);
    }

    public void notNewUser(UserData userData, FlightPriceTrackerTelegramBot bot,UserFlightData userFlightData) {
        context = BotStateContextRepo.of(bot, userData, text, callbackQuery, userFlightData);
        state = BotState.byId(userData.getStateID());
    }

}
