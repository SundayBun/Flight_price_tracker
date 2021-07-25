package com.example.flight_price_tracker_telegram.bot.refactored.service.buttonsCallback;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class DataTransferredStateKeyBoard extends KeyboardMarkups{
    @Override
    public void createKeyboardMarkup() {
        buttonList = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButtonTrack = new InlineKeyboardButton();
        inlineKeyboardButtonTrack.setText("Track it");
        inlineKeyboardButtonTrack.setCallbackData(buttons.get("Track it"));
        buttonList.add(inlineKeyboardButtonTrack);
        keyboardButtonsRows.add(buttonList);
    }
}
