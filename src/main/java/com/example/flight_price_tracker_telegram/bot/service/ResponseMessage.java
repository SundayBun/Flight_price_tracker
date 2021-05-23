package com.example.flight_price_tracker_telegram.bot.service;

import com.example.flight_price_tracker_telegram.bot.BotState;

import com.example.flight_price_tracker_telegram.bot.BotStateContextRepo;
import com.example.flight_price_tracker_telegram.model.browse.FlightPricesDTO;
import com.example.flight_price_tracker_telegram.model.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.model.places.PlacesDTO;
import com.example.flight_price_tracker_telegram.model.service.ILocalisationClient;
import com.example.flight_price_tracker_telegram.model.service.LocalisationClientImpl;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Slf4j
public class ResponseMessage {

    public static SendMessage sendMessage(BotStateContextRepo context, BotState state, boolean queryResponse, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(context.getUserData().getChatId().toString());

        if (!queryResponse) {
            message.setText(text);
            if(state==BotState.ORIGIN_PLACE_TEXT){
                message.setReplyMarkup(ButtonHandlerV2.getMainMenuKeyboard());
            }
        } else {
            message.setText(text);
            message.setReplyMarkup(ButtonHandlerV2.getMessageFromKeyboard(state));
        }
        return message;

    }

    public static SendMessage sendSearchResult(BotStateContextRepo context, BotState state) {
        SendMessage message = new SendMessage();
        message.setChatId(context.getUserFlightData().getChatId().toString());

        message.setText(context.getUserFlightData().getSkyScannerResponseDates().toString());
        message.setReplyMarkup(ButtonHandlerV2.getMessageFromKeyboard(state));

        log.info("sendSearchResult setText={}", message.getText());
        return message;
    }

    public static SendMessage sendSearchCountry(BotStateContextRepo context,List<CountryDTO> countryDTOList, String text){
        SendMessage message = new SendMessage();
        message.setChatId(context.getUserData().getChatId().toString());
        message.setReplyMarkup(ButtonHandlerV2.getMessageFromKeyboardCountry(countryDTOList));
        message.setText(text);
        return message;
    }

    public static SendMessage sendSearchPlaces(BotStateContextRepo context,List<PlacesDTO> placesDTOList, String text){
        SendMessage message = new SendMessage();
        message.setChatId(context.getUserData().getChatId().toString());
        message.setReplyMarkup(ButtonHandlerV2.getMessageFromKeyboardPlaces(placesDTOList));
        message.setText(text);
        return message;
    }

    public static AnswerCallbackQuery sendSubConfirmation(BotStateContextRepo context,String text){
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(context.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(true);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }

}
