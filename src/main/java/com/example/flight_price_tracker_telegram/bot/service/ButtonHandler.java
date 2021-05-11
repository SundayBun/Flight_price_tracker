package com.example.flight_price_tracker_telegram.bot.service;

import com.example.flight_price_tracker_telegram.bot.BotState;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ButtonHandler {

    public static InlineKeyboardMarkup getMessageFromKeyboard(BotState botState) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

//if (botState.ordinal()==0) потом по state можно вызывать разные клавиатуры

//creating the buttons
        InlineKeyboardButton inlineKeyboardButtonYes = new InlineKeyboardButton();
        inlineKeyboardButtonYes.setText("Find price");

        //Every button must have callBackData, or else it will not work !
        inlineKeyboardButtonYes.setCallbackData("Button \"Find price\" has been pressed");


        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButtonYes);

        List< List<InlineKeyboardButton>> keyboardButtonsRow=new ArrayList<>();
        keyboardButtonsRow.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(keyboardButtonsRow);
        return inlineKeyboardMarkup;
    }
}
