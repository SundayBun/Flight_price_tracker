package com.example.flight_price_tracker_telegram.bot.refactored.service.buttonsCallback;

import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;

public class StartStateKeyBoard extends KeyboardMarkups{


    @Override
    public void createKeyboardMarkup() {
        buttonList = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButtonEng = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButtonRus = new InlineKeyboardButton();
        inlineKeyboardButtonEng.setText(Emojis.GB_FLAG.toString());
        inlineKeyboardButtonEng.setCallbackData(buttons.get(Emojis.GB_FLAG.toString()));
        inlineKeyboardButtonRus.setText(Emojis.RUS_FLAG.toString());
        inlineKeyboardButtonRus.setCallbackData(buttons.get(Emojis.RUS_FLAG.toString()));

        buttonList.add(inlineKeyboardButtonEng);
        buttonList.add(inlineKeyboardButtonRus);

        keyboardButtonsRows.add(buttonList);
    }
}
