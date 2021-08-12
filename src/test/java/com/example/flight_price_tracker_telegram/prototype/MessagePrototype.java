package com.example.flight_price_tracker_telegram.prototype;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

public class MessagePrototype {
    public static  Chat chat=ChatPrototype.aChat();
    public static String text="test_textMessage";

    public static Message aMessage(){
        Message message=new Message();
        message.setChat(chat);
        message.setText(text);
        return message;
    }
}
