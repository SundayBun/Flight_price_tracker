package com.example.flight_price_tracker_telegram.bot.service;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import com.example.flight_price_tracker_telegram.skyscanner_api.dto.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.skyscanner_api.dto.places.PlacesDTO;
import com.example.flight_price_tracker_telegram.repository.entity.UserSubscription;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ButtonHandler {

    public static Map<String, String> buttons;

    public static Map<String, String> toMap() {

        buttons = new HashMap<>();
        buttons.put(Emojis.GB_FLAG.toString(), "Button \"ENG\" has been pressed");
        buttons.put(Emojis.RUS_FLAG.toString(), "Button \"RUS\" has been pressed");
        buttons.put(Emojis.PLANE.toString(), "Button \"Find price\" has been pressed");
        buttons.put("USD", "USD");
        buttons.put("EUR", "EUR");
        buttons.put("RUB", "RUB");
        buttons.put("Track it", "Button \"Track it\" has been pressed");
        buttons.put("One way", "Button \"One way\" has been pressed");
        return buttons;

    }

    public static InlineKeyboardMarkup getMessageFromKeyboard(Context context) {

        buttons = toMap();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

        switch (context.getState().getStateName()) {
            case START: {
                InlineKeyboardButton inlineKeyboardButtonEng = new InlineKeyboardButton();
                InlineKeyboardButton inlineKeyboardButtonRus = new InlineKeyboardButton();
                inlineKeyboardButtonEng.setText(Emojis.GB_FLAG.toString());
                inlineKeyboardButtonEng.setCallbackData(buttons.get(Emojis.GB_FLAG.toString()));
                inlineKeyboardButtonRus.setText(Emojis.RUS_FLAG.toString());
                inlineKeyboardButtonRus.setCallbackData(buttons.get(Emojis.RUS_FLAG.toString()));
                keyboardButtonsRow.add(inlineKeyboardButtonEng);
                keyboardButtonsRow.add(inlineKeyboardButtonRus);

                List<List<InlineKeyboardButton>> keyboardButtonsRows = new ArrayList<>();
                keyboardButtonsRows.add(keyboardButtonsRow);

                inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
                return inlineKeyboardMarkup;
            }
            case DATA_FILLED: {

                InlineKeyboardButton inlineKeyboardButtonFind = new InlineKeyboardButton();
                inlineKeyboardButtonFind.setText(Emojis.PLANE.toString());
                inlineKeyboardButtonFind.setCallbackData(buttons.get(Emojis.PLANE.toString()));
                keyboardButtonsRow.add(inlineKeyboardButtonFind);

                List<List<InlineKeyboardButton>> keyboardButtonsRows = new ArrayList<>();
                keyboardButtonsRows.add(keyboardButtonsRow);

                inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
                return inlineKeyboardMarkup;
            }
            case CURRENCY: {

                InlineKeyboardButton inlineKeyboardButtonUSD = new InlineKeyboardButton();
                InlineKeyboardButton inlineKeyboardButtonEUR = new InlineKeyboardButton();
                InlineKeyboardButton inlineKeyboardButtonRUB = new InlineKeyboardButton();

                inlineKeyboardButtonUSD.setText("USD");
                inlineKeyboardButtonUSD.setCallbackData(buttons.get("USD"));
                inlineKeyboardButtonEUR.setText("EUR");
                inlineKeyboardButtonEUR.setCallbackData(buttons.get("EUR"));
                inlineKeyboardButtonRUB.setText("RUB");
                inlineKeyboardButtonRUB.setCallbackData(buttons.get("RUB"));

                keyboardButtonsRow.add(inlineKeyboardButtonUSD);
                keyboardButtonsRow.add(inlineKeyboardButtonEUR);
                keyboardButtonsRow.add(inlineKeyboardButtonRUB);

                List<List<InlineKeyboardButton>> keyboardButtonsRows = new ArrayList<>();
                keyboardButtonsRows.add(keyboardButtonsRow);

                inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
                return inlineKeyboardMarkup;
            }
            case DATA_TRANSFERRED: {

                InlineKeyboardButton inlineKeyboardButtonTrack = new InlineKeyboardButton();
                inlineKeyboardButtonTrack.setText("Track it");
                inlineKeyboardButtonTrack.setCallbackData(buttons.get("Track it"));
                keyboardButtonsRow.add(inlineKeyboardButtonTrack);

                List<List<InlineKeyboardButton>> keyboardButtonsRows = new ArrayList<>();
                keyboardButtonsRows.add(keyboardButtonsRow);

                inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
                return inlineKeyboardMarkup;
            }
            case INBOUND_PARTIAL_DATE: {

                InlineKeyboardButton inlineKeyboardOneWay = new InlineKeyboardButton();
                inlineKeyboardOneWay.setText("One way");
                inlineKeyboardOneWay.setCallbackData(buttons.get("One way"));
                keyboardButtonsRow.add(inlineKeyboardOneWay);

                List<List<InlineKeyboardButton>> keyboardButtonsRows = new ArrayList<>();
                keyboardButtonsRows.add(keyboardButtonsRow);

                inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
                return inlineKeyboardMarkup;
            }
            default:
                return null;
        }
    }

    public static InlineKeyboardMarkup getMessageFromKeyboardCountry(List<CountryDTO> countryDTOList) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardButtonsRows = new ArrayList<>();


        for (CountryDTO country : countryDTOList) {
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();

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
        List<List<InlineKeyboardButton>> keyboardButtonsRows = new ArrayList<>();

        for (PlacesDTO places : placesDTOList) {
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();

            inlineKeyboardButton.setText(places.getPlaceName()+", "+places.getCountryName());
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

        List<List<InlineKeyboardButton>> keyboardButtonsRows = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        int n = 1;

        for (UserSubscription subscription : subscriptionList) {

            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("" + n);
            // inlineKeyboardButton.setCallbackData(subscription.getId());
            inlineKeyboardButton.setCallbackData("" + subscriptionList.indexOf(subscription));
            n++;
            keyboardButtonsRow.add(inlineKeyboardButton);

        }

        keyboardButtonsRows.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
        return inlineKeyboardMarkup;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}

