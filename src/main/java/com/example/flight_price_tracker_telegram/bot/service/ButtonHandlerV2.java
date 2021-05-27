package com.example.flight_price_tracker_telegram.bot.service;

import com.example.flight_price_tracker_telegram.bot.BotState;
import com.example.flight_price_tracker_telegram.bot.BotStateContextRepo;
import com.example.flight_price_tracker_telegram.model.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.model.places.PlacesDTO;
import com.example.flight_price_tracker_telegram.model.service.ILocalisationClient;
import com.example.flight_price_tracker_telegram.model.service.LocalisationClientImpl;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

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

        } else if(state == BotState.DATA_TRANSFERRED){
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

            InlineKeyboardButton inlineKeyboardButtonTrack = new InlineKeyboardButton();
            inlineKeyboardButtonTrack.setText("Track it");
            inlineKeyboardButtonTrack.setCallbackData("Button \"Track it\" has been pressed");
            keyboardButtonsRow.add(inlineKeyboardButtonTrack);

            List<List<InlineKeyboardButton>> keyboardButtonsRows = new ArrayList<>();
            keyboardButtonsRows.add(keyboardButtonsRow);

            inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
            return inlineKeyboardMarkup;
        }
        return null;
    }

    public static InlineKeyboardMarkup getMessageFromKeyboardCountry(List<CountryDTO> countryDTOList) {

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List< List<InlineKeyboardButton>> keyboardButtonsRows=new ArrayList<>();


            for (CountryDTO country:countryDTOList){
                List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
                InlineKeyboardButton inlineKeyboardButton=new InlineKeyboardButton();

                inlineKeyboardButton.setText(country.getName());
                inlineKeyboardButton.setCallbackData(country.getCode());

                keyboardButtonsRow.add(inlineKeyboardButton);
                keyboardButtonsRows.add(keyboardButtonsRow);
            }

            inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
            return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getMessageFromKeyboardPlaces(List<PlacesDTO> placesDTOList) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List< List<InlineKeyboardButton>> keyboardButtonsRows=new ArrayList<>();

        for (PlacesDTO places:placesDTOList){
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton=new InlineKeyboardButton();

            inlineKeyboardButton.setText(places.getPlaceName());
            inlineKeyboardButton.setCallbackData(places.getPlaceId());

            keyboardButtonsRow.add(inlineKeyboardButton);
            keyboardButtonsRows.add(keyboardButtonsRow);
        }

        inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
        return inlineKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getMainMenuKeyboard() {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        row1.add(new KeyboardButton("Change localisation info"));
        row2.add(new KeyboardButton("New search"));
        row3.add(new KeyboardButton("See your track list"));
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getMessageFromKeyboardSubList(List<UserSubscription> subscriptionList) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List< List<InlineKeyboardButton>> keyboardButtonsRows=new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        int n=1;

        for (UserSubscription subscription:subscriptionList){

            InlineKeyboardButton inlineKeyboardButton=new InlineKeyboardButton();
            inlineKeyboardButton.setText(""+n);
           // inlineKeyboardButton.setCallbackData(subscription.getId());
            inlineKeyboardButton.setCallbackData(""+subscriptionList.indexOf(subscription));
            n++;
            keyboardButtonsRow.add(inlineKeyboardButton);

        }

        keyboardButtonsRows.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
        return inlineKeyboardMarkup;
    }

}

