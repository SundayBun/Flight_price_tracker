package com.example.flight_price_tracker_telegram.prototype;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class SendMessagePrototype {
    public static final String chatId="test_chatId";
    public static final String text ="test_text";

    public static BotApiMethod<?> aSendMessage(){
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        return sendMessage;
    }
}
