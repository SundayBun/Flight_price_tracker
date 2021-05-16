package com.example.flight_price_tracker_telegram.bot.service;

import com.example.flight_price_tracker_telegram.bot.BotState;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class ButtonHandlerV2 {

    public static InlineKeyboardMarkup getMessageFromKeyboard(BotState state) {
        if (state == BotState.START) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

            InlineKeyboardButton inlineKeyboardButtonEng = new InlineKeyboardButton();
            InlineKeyboardButton inlineKeyboardButtonRus = new InlineKeyboardButton();
            inlineKeyboardButtonEng.setText("ENG");
            inlineKeyboardButtonEng.setCallbackData("Button \"ENG\" has been pressed");
            inlineKeyboardButtonRus.setText("RUS");
            inlineKeyboardButtonRus.setCallbackData("Button \"RUS\" has been pressed");
            keyboardButtonsRow.add(inlineKeyboardButtonEng);
            keyboardButtonsRow.add(inlineKeyboardButtonRus);

            List<List<InlineKeyboardButton>> keyboardButtonsRows = new ArrayList<>();
            keyboardButtonsRows.add(keyboardButtonsRow);

            inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
            return inlineKeyboardMarkup;
        } else if (state == BotState.DATA_FILLED) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

            InlineKeyboardButton inlineKeyboardButtonFind = new InlineKeyboardButton();
            InlineKeyboardButton inlineKeyboardButtonReturn = new InlineKeyboardButton();
            inlineKeyboardButtonFind.setText("Find price");
            inlineKeyboardButtonFind.setCallbackData("Button \"Find price\" has been pressed");
            keyboardButtonsRow.add(inlineKeyboardButtonFind);

            inlineKeyboardButtonReturn.setText("Change data");
            inlineKeyboardButtonReturn.setCallbackData("Button \"Change data\" has been pressed");
            keyboardButtonsRow.add(inlineKeyboardButtonReturn);

            List<List<InlineKeyboardButton>> keyboardButtonsRows = new ArrayList<>();
            keyboardButtonsRows.add(keyboardButtonsRow);

            inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
            return inlineKeyboardMarkup;
        }else if(state == BotState.COUNTRY_BUTTONS){

        }
        return null;
    }

}

