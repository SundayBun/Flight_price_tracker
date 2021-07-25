package com.example.flight_price_tracker_telegram.bot.refactored.service.buttonsCallback;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import com.example.flight_price_tracker_telegram.model.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.model.places.PlacesDTO;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.*;

public abstract class KeyboardMarkups {


    public static Map<String, String> buttons;
    public List<InlineKeyboardButton> buttonList;
    public List<List<InlineKeyboardButton>> keyboardButtonsRows;
    public List<KeyboardRow> keyboard;

    public List<CountryDTO> countryDTOList;
    public List<PlacesDTO> placesDTOList;
    public List<UserSubscription> subscriptionList;

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

    public InlineKeyboardMarkup getMessageFromKeyboard() {
        buttons = toMap();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        keyboardButtonsRows = new ArrayList<>();

        createKeyboardMarkup();

        inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getMessageFromKeyboardCountry(List<CountryDTO> countryDTOList) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        keyboardButtonsRows = new ArrayList<>();

        this.countryDTOList = countryDTOList;
        createKeyboardMarkup();

        inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getMessageFromKeyboardPlaces(List<PlacesDTO> placesDTOList) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        keyboardButtonsRows = new ArrayList<>();

        this.placesDTOList = placesDTOList;
        createKeyboardMarkup();

        inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
        return inlineKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        createKeyboardMarkup();

        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup getMessageFromKeyboardSubList(List<UserSubscription> subscriptionList) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        keyboardButtonsRows = new ArrayList<>();

        this.subscriptionList = subscriptionList;
        createKeyboardMarkup();

        inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
        return inlineKeyboardMarkup;
    }

    public abstract void createKeyboardMarkup();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyboardMarkups that = (KeyboardMarkups) o;
        return Objects.equals(buttonList, that.buttonList) && Objects.equals(keyboardButtonsRows, that.keyboardButtonsRows) && Objects.equals(keyboard, that.keyboard) && Objects.equals(countryDTOList, that.countryDTOList) && Objects.equals(placesDTOList, that.placesDTOList) && Objects.equals(subscriptionList, that.subscriptionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buttonList, keyboardButtonsRows, keyboard, countryDTOList, placesDTOList, subscriptionList);
    }
}
