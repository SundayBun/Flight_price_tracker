package com.example.flight_price_tracker_telegram.bot.refactored.service.buttonsCallback;

import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class DataFilledStateKeyBoard extends KeyboardMarkups{

    @Override
    public void createKeyboardMarkup() {
        buttonList = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButtonFind = new InlineKeyboardButton();
        inlineKeyboardButtonFind.setText(Emojis.PLANE.toString());
        inlineKeyboardButtonFind.setCallbackData(buttons.get(Emojis.PLANE.toString()));
        buttonList.add(inlineKeyboardButtonFind);
        keyboardButtonsRows.add(buttonList);
    }
}
