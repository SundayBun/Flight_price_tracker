package com.example.flight_price_tracker_telegram.bot.refactored.service.buttonsCallback;

import com.example.flight_price_tracker_telegram.model.places.PlacesDTO;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class PlacesStatesKeyBoard extends KeyboardMarkups{
    @Override
    public void createKeyboardMarkup() {
       keyboardButtonsRows=new ArrayList<>();

        for (PlacesDTO places:placesDTOList){
            buttonList = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton=new InlineKeyboardButton();

            inlineKeyboardButton.setText(places.getPlaceName());
            inlineKeyboardButton.setCallbackData(places.getPlaceId());

            buttonList.add(inlineKeyboardButton);
            keyboardButtonsRows.add(buttonList);
        }
    }
}
