package com.example.flight_price_tracker_telegram.bot.refactored.service.buttonsCallback;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class CurrencyStateKeyBoard extends KeyboardMarkups{
    @Override
    public void createKeyboardMarkup() {
        buttonList = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButtonUSD = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButtonEUR = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButtonRUB = new InlineKeyboardButton();

        inlineKeyboardButtonUSD.setText("USD");
        inlineKeyboardButtonUSD.setCallbackData(buttons.get("USD"));
        inlineKeyboardButtonEUR.setText("EUR");
        inlineKeyboardButtonEUR.setCallbackData(buttons.get("EUR"));
        inlineKeyboardButtonRUB.setText("RUB");
        inlineKeyboardButtonRUB.setCallbackData(buttons.get("RUB"));

        buttonList.add(inlineKeyboardButtonUSD);
        buttonList.add(inlineKeyboardButtonEUR);
        buttonList.add(inlineKeyboardButtonRUB);
        keyboardButtonsRows.add(buttonList);
    }
}
