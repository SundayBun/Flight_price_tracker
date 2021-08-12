package com.example.flight_price_tracker_telegram.prototype;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ChatPrototype {
    public static  Long id=UsersPrototype.chatId;

    public static Chat aChat(){
        Chat chat=new Chat();
        chat.setId(id);
        return chat;
    }
}
