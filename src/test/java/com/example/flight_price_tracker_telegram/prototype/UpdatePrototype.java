package com.example.flight_price_tracker_telegram.prototype;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdatePrototype {

    public static Message message = MessagePrototype.aMessage();
    public static CallbackQuery callbackQuery = CallbackQueryPrototype.aCallbackQuery();

    public static Update aUpdateMessage() {
        Update update = new Update();
        update.setMessage(message);
        return update;
    }

    public static Update aUpdateCallbackQuery() {
        Update update = new Update();
        update.setCallbackQuery(callbackQuery);
        return update;
    }

    public static Update aUpdateMessageCallbackQuery() {
        Update update = new Update();
        update.setMessage(message);
        update.setCallbackQuery(callbackQuery);
        return update;
    }
}
