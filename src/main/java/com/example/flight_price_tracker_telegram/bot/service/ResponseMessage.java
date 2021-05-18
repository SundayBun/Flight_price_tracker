package com.example.flight_price_tracker_telegram.bot.service;

import com.example.flight_price_tracker_telegram.bot.BotState;

import com.example.flight_price_tracker_telegram.bot.BotStateContextRepo;
import com.example.flight_price_tracker_telegram.model.browse.FlightPricesDTO;
import com.example.flight_price_tracker_telegram.model.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.model.places.PlacesDTO;
import com.example.flight_price_tracker_telegram.model.service.ILocalisationClient;
import com.example.flight_price_tracker_telegram.model.service.LocalisationClientImpl;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Slf4j
public class ResponseMessage {

    public static SendMessage sendMessage(BotStateContextRepo context, BotState state, boolean queryResponse, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(context.getUserData().getChatId().toString());

        if (!queryResponse) {
            message.setText(text);
        } else {
            message.setText(text);
            message.setReplyMarkup(ButtonHandlerV2.getMessageFromKeyboard(state));
        }

        return message;

    }

    public static SendMessage sendSearchResult(BotStateContextRepo context) {
        SendMessage message = new SendMessage();
        message.setChatId(context.getUserFlightData().getChatId().toString());

       // message.setText(context.getUserFlightData().getSkyScannerResponse().toString());

        message.setText(context.getUserFlightData().getSkyScannerResponse().getPlaces()
               +"\n " + context.getUserFlightData().getOutboundPartialDate()
                +"\n " + context.getUserFlightData().getInboundPartialDate()
                +"\n "  + context.getUserFlightData().getSkyScannerResponse().getCarriers()
                +"\n "   + context.getUserFlightData().getSkyScannerResponse().getQuotes()
                + context.getUserFlightData().getSkyScannerResponse().getCurrencies());

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


}
