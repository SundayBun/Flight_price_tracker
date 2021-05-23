package com.example.flight_price_tracker_telegram.bot;

import com.example.flight_price_tracker_telegram.model.browse.BrowsePlacesDTO;
import com.example.flight_price_tracker_telegram.model.browse.CarriersDTO;
import com.example.flight_price_tracker_telegram.model.browse.FlightPricesDTO;
import com.example.flight_price_tracker_telegram.model.browse.QuotesDTO;
import com.example.flight_price_tracker_telegram.model.exception.FlightClientException;
import com.example.flight_price_tracker_telegram.model.localisation.CurrencyDTO;
import com.example.flight_price_tracker_telegram.model.service.UniRestServiceImpl;
import com.example.flight_price_tracker_telegram.model.validations.ValidationErrorDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TFV3 {


    private static CallbackQuery callbackQuery;
    private static String chatId;
    private static String text;

    public static SendMessage handleUpdate(Update update, FlightPriceTrackerTelegramBot bot) {
        SendMessage sendMessage = new SendMessage();


        if (!update.hasCallbackQuery()) {
            chatId = update.getMessage().getChatId().toString();
            text = update.getMessage().getText();
            sendMessage.setChatId(chatId);
            sendMessage.setReplyMarkup(getMainMenuKeyboard());
            sendMessage.setText(text);
            log.info(update.getMessage().getText());

        } else {
            callbackQuery = update.getCallbackQuery();
            sendMessage.enableMarkdown(true);
            sendMessage.setText("callbackQuery.getData()");
            log.info(update.getCallbackQuery().getData());
        }
return sendMessage;

    }

    public static ReplyKeyboardMarkup getMainMenuKeyboard() {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

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
        return replyKeyboardMarkup;
    }
}