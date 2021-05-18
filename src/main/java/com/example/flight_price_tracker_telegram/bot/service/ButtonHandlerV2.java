package com.example.flight_price_tracker_telegram.bot.service;

import com.example.flight_price_tracker_telegram.bot.BotState;
import com.example.flight_price_tracker_telegram.bot.BotStateContextRepo;
import com.example.flight_price_tracker_telegram.model.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.model.places.PlacesDTO;
import com.example.flight_price_tracker_telegram.model.service.ILocalisationClient;
import com.example.flight_price_tracker_telegram.model.service.LocalisationClientImpl;
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
        } else if (state == BotState.CURRENCY) {

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

            InlineKeyboardButton inlineKeyboardButtonUSD = new InlineKeyboardButton();
            InlineKeyboardButton inlineKeyboardButtonEUR = new InlineKeyboardButton();
            InlineKeyboardButton inlineKeyboardButtonRUB = new InlineKeyboardButton();

            inlineKeyboardButtonUSD.setText("USD");
            inlineKeyboardButtonUSD.setCallbackData("USD");
            inlineKeyboardButtonEUR.setText("EUR");
            inlineKeyboardButtonEUR.setCallbackData("EUR");
            inlineKeyboardButtonRUB.setText("RUB");
            inlineKeyboardButtonRUB.setCallbackData("RUB");

            keyboardButtonsRow.add(inlineKeyboardButtonUSD);
            keyboardButtonsRow.add(inlineKeyboardButtonEUR);
            keyboardButtonsRow.add(inlineKeyboardButtonRUB);

            List<List<InlineKeyboardButton>> keyboardButtonsRows = new ArrayList<>();
            keyboardButtonsRows.add(keyboardButtonsRow);

            inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
            return inlineKeyboardMarkup;

        }
        return null;
    }

    public static InlineKeyboardMarkup getMessageFromKeyboardCountry(List<CountryDTO> countryDTOList) {

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

            for (CountryDTO country:countryDTOList){
                InlineKeyboardButton inlineKeyboardButton=new InlineKeyboardButton();
                inlineKeyboardButton.setText(country.getName());
                inlineKeyboardButton.setCallbackData(country.getCode());
                keyboardButtonsRow.add(inlineKeyboardButton);
            }

            List< List<InlineKeyboardButton>> keyboardButtonsRows=new ArrayList<>();
            keyboardButtonsRows.add(keyboardButtonsRow);

            inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
            return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getMessageFromKeyboardPlaces(List<PlacesDTO> placesDTOList) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

        for (PlacesDTO places:placesDTOList){
            InlineKeyboardButton inlineKeyboardButton=new InlineKeyboardButton();
            inlineKeyboardButton.setText(places.getPlaceName());
            inlineKeyboardButton.setCallbackData(places.getCityId());
            keyboardButtonsRow.add(inlineKeyboardButton);
        }

        List< List<InlineKeyboardButton>> keyboardButtonsRows=new ArrayList<>();
        keyboardButtonsRows.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
        return inlineKeyboardMarkup;
    }

}

