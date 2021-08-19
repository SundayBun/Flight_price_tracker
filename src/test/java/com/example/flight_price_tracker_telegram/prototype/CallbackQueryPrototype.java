package com.example.flight_price_tracker_telegram.prototype;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Data
public class CallbackQueryPrototype {
    public static String id = UsersPrototype.id;
    public static String data = "some_dataCallbackQuery";

    public static CallbackQuery aCallbackQuery() {
        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setData(data);
        callbackQuery.setId(id);
        callbackQuery.setMessage(new Message());
        callbackQuery.getMessage().setChat(ChatPrototype.aChat());
        return callbackQuery;
    }
}
