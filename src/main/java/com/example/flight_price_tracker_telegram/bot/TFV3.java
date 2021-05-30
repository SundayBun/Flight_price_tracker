package com.example.flight_price_tracker_telegram.bot;

import com.example.flight_price_tracker_telegram.model.browse.BrowsePlacesDTO;
import com.example.flight_price_tracker_telegram.model.browse.CarriersDTO;
import com.example.flight_price_tracker_telegram.model.browse.FlightPricesDTO;
import com.example.flight_price_tracker_telegram.model.browse.QuotesDTO;
import com.example.flight_price_tracker_telegram.model.exception.FlightClientException;
import com.example.flight_price_tracker_telegram.model.localisation.CurrencyDTO;
import com.example.flight_price_tracker_telegram.model.service.UniRestServiceImpl;
import com.example.flight_price_tracker_telegram.model.validations.ValidationErrorDTO;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@PropertySource("classpath:/static/flights.html")
public class TFV3 {



    private static CallbackQuery callbackQuery;
    private static String chatId;
    private static String text;



    public static BotApiMethod<?> handleUpdate(Update update, FlightPriceTrackerTelegramBot bot) {

        SendMessage sendMessage = new SendMessage();

        MessageEntity messageEntity=new MessageEntity();
        messageEntity.setUrl("Delete");


//        List <MessageEntity> entities=new ArrayList<>();
//        entities.add(messageEntity);

        List<String> list=new ArrayList<>();
        for(int i=0;i<20;i++) {
            list.add("Page "+i+" text");
        }

        EditMessageText editMessageText=new EditMessageText();

        if (!update.hasCallbackQuery()) {
String text="/delete";

            chatId=update.getMessage().getChatId().toString();
            sendMessage.setChatId(chatId);
            sendMessage.setReplyMarkup(getMainMenuKeyboard(list));
           // sendMessage.setText(list.get(0));
            //sendMessage.setEntities(entities);
         //   sendMessage.setParseMode("HTML");
            //sendMessage.enableHtml(true);
           // sendMessage.setText("<a href=\"https://core.telegram.org/bots/api#formatting-options/\">inline URL</a>");
            sendMessage.setText("/flights");
            log.info(update.getMessage().getText());

            return sendMessage;
        }else {
            //Integer messageID=update.getCallbackQuery().getInlineMessageId();
            Integer messageID = update.getCallbackQuery().getMessage().getMessageId();
            editMessageText.setChatId(chatId);
            editMessageText.setMessageId(messageID);
            editMessageText.setText(list.get(Integer.parseInt(update.getCallbackQuery().getData())-1));
            editMessageText.setReplyMarkup(getMainMenuKeyboard(list));

            log.info(update.getCallbackQuery().getData());
            return editMessageText;
        }

    }

    public static InlineKeyboardMarkup getMainMenuKeyboard(List<String> subscriptionList) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List< List<InlineKeyboardButton>> keyboardButtonsRows=new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        int n=1;

        for (String subscription:subscriptionList){
            InlineKeyboardButton inlineKeyboardButton=new InlineKeyboardButton();

            inlineKeyboardButton.setText(""+n);
            inlineKeyboardButton.setCallbackData(""+n);
            n++;

            keyboardButtonsRow.add(inlineKeyboardButton);
        }
        keyboardButtonsRows.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(keyboardButtonsRows);
        return inlineKeyboardMarkup;
    }
}