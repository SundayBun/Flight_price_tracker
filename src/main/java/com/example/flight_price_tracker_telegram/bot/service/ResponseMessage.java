package com.example.flight_price_tracker_telegram.bot.service;

import com.example.flight_price_tracker_telegram.bot.BotState;

import com.example.flight_price_tracker_telegram.bot.BotStateContextRepo;
import com.example.flight_price_tracker_telegram.model.browse.FlightPricesDTO;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
public class ResponseMessage {

    public static SendMessage sendMessage(BotStateContextRepo context, BotState state, boolean queryResponse , String text) {
        SendMessage message=new SendMessage();
        message.setChatId(context.getUserData().getChatId().toString());

        if(!queryResponse) {
            message.setText(text);
        } else{
            message.setText(text);
            message.setReplyMarkup(ButtonHandlerV2.getMessageFromKeyboard(state));
        }

        return message;

    }

    public static SendMessage sendSearchResult(BotStateContextRepo context){
        SendMessage message=new SendMessage();
        message.setChatId(context.getUserFlightData().getChatId().toString());

        message.setText(context.getUserFlightData().getSkyScannerResponse().toString());

        log.info("sendSearchResult setText={}",message.getText());
        return message;
    }
}
