package com.example.flight_price_tracker_telegram.bot.service;

import com.example.flight_price_tracker_telegram.bot.BotState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

//@PropertySource("classpath:/static/question.properties")
public class ButtonHandler {

    public static List<String> listOfRequests;

    public static InlineKeyboardMarkup getMessageFromKeyboard(BotState botState) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

        getRequiredList(botState);

        for (String question:listOfRequests){
            InlineKeyboardButton inlineKeyboardButton=new InlineKeyboardButton();
            inlineKeyboardButton.setText(question);
            inlineKeyboardButton.setCallbackData("Button \""+question+"\" has been pressed");
            keyboardButtonsRow.add(inlineKeyboardButton);
        }

        List< List<InlineKeyboardButton>> keyboardButtonsRows=new ArrayList<>();
        keyboardButtonsRows.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
        return inlineKeyboardMarkup;
    }

    public static void getRequiredList(BotState botState){
        listOfRequests=new ArrayList<>();
        switch (botState.ordinal()){
            case 0:
                listOfRequests.add("ENG");
                listOfRequests.add("RUS");
            case 8:
                listOfRequests.add("Find price");
            default: break;
        }
    }

}
