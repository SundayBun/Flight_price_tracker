package com.example.flight_price_tracker_telegram.bot.refactored.service.buttonsCallback;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;

public class InboundPartialDateStateKeyBoard extends KeyboardMarkups{
    @Override
    public void createKeyboardMarkup() {
        buttonList= new ArrayList<>();

        InlineKeyboardButton inlineKeyboardOneWay = new InlineKeyboardButton();
        inlineKeyboardOneWay.setText("One way");
        inlineKeyboardOneWay.setCallbackData(buttons.get("One way"));
        buttonList.add(inlineKeyboardOneWay);

        keyboardButtonsRows.add(buttonList);
    }
}
