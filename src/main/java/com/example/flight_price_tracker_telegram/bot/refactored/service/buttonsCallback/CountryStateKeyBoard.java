package com.example.flight_price_tracker_telegram.bot.refactored.service.buttonsCallback;

import com.example.flight_price_tracker_telegram.model.localisation.CountryDTO;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class CountryStateKeyBoard extends KeyboardMarkups{

    @Override
    public void createKeyboardMarkup(){

        for (CountryDTO country:countryDTOList){
            buttonList = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton=new InlineKeyboardButton();

            inlineKeyboardButton.setText(country.getName());
            inlineKeyboardButton.setCallbackData(country.getCode());

            buttonList.add(inlineKeyboardButton);
            keyboardButtonsRows.add(buttonList);
        }
    };
}
