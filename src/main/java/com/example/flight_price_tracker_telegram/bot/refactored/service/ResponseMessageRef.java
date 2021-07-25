package com.example.flight_price_tracker_telegram.bot.refactored.service;


import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.states.OriginPlaceText;
import com.example.flight_price_tracker_telegram.bot.service.ButtonHandler;
import com.example.flight_price_tracker_telegram.model.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.model.places.PlacesDTO;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.List;

@Slf4j

public class ResponseMessageRef {

    public static SendMessage sendMessage(Context context, String text) {

        SendMessage message = new SendMessage();
        message.setChatId(context.getUserData().getChatId().toString());

        if (!context.getState().isQueryResponse()) {
            message.setText(text);
            if (context.getState().equals(new OriginPlaceText(context))) {
                message.setReplyMarkup(ButtonHandlerRef.getMainMenuKeyboard());
            }
        } else {
            message.setText(text);
            message.setReplyMarkup(ButtonHandlerRef.getMessageFromKeyboard(context));
        }
        return message;

    }

    public static SendMessage sendSearchResult(Context context) {

        SendMessage message = new SendMessage();
        message.setChatId(context.getUserFlightData().getChatId().toString());

        if (context.getUserFlightData().getInboundPartialDate() == null) {

            String textOneWay = "-------------FLIGHT INFO-------------" + "\n" +
                    "\n   Origin place: " + context.getUserFlightData().getSkyScannerResponseQuotes().getPlaces().get(0).getCityName() +
                    "\n   Destination place: " + context.getUserFlightData().getSkyScannerResponseQuotes().getPlaces().get(1).getCityName() + "\n" +
                    "\n   Outbound partial date: " + context.getUserFlightData().getOutboundPartialDate() +
                    "\n   Min price: " + context.getUserFlightData().getSkyScannerResponseQuotes().getQuotes().get(0).getMinPrice() +
                    context.getUserFlightData().getSkyScannerResponseQuotes().getCurrencies().get(0).getSymbol() + "\n" +
                    "\n   Carrier: " + context.getUserFlightData().getSkyScannerResponseQuotes().getCarriers();

            message.setText(textOneWay);
        } else {
            String textTwoWays = "-------------FLIGHT INFO-------------" + "\n" +
                    "\n   Origin place: " + context.getUserFlightData().getSkyScannerResponseDates().getPlaces().get(0).getCityName() +
                    "\n   Destination place: " + context.getUserFlightData().getSkyScannerResponseDates().getPlaces().get(1).getCityName() + "\n" +
                    "\n   Outbound partial date: " + context.getUserFlightData().getSkyScannerResponseDates().getDates().getOutboundDates().get(0).getPartialDate() +
//                "\n   Min price: " + context.getUserFlightData().getSkyScannerResponseDates().getDates().getOutboundDates().get(0).getPrice() +
//                context.getUserFlightData().getSkyScannerResponseDates().getCurrencies().get(0).getSymbol() + "\n" +
                    "\n   Inbound partial date: " + context.getUserFlightData().getSkyScannerResponseDates().getDates().getInboundDates().get(0).getPartialDate() +
//                "\n   Min price: " + context.getUserFlightData().getSkyScannerResponseDates().getDates().getInboundDates().get(0).getPrice() +
//                context.getUserFlightData().getSkyScannerResponseDates().getCurrencies().get(0).getSymbol() + "\n" +
                    "\n   Min price: " + context.getUserFlightData().getSkyScannerResponseDates().getQuotes().get(0).getMinPrice() +
                    context.getUserFlightData().getSkyScannerResponseDates().getCurrencies().get(0).getSymbol() + "\n" +
                    "\n   Carrier: " + context.getUserFlightData().getSkyScannerResponseDates().getCarriers();
            message.setText(textTwoWays);
        }

        message.setReplyMarkup(ButtonHandlerRef.getMessageFromKeyboard(context));

        log.info("sendSearchResult setText={}", message.getText());
        return message;
    }

    public static SendMessage sendSearchCountry(Context context, List<CountryDTO> countryDTOList, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(context.getUserData().getChatId().toString());
        message.setReplyMarkup(ButtonHandlerRef.getMessageFromKeyboardCountry(countryDTOList));
        message.setText(text);

        return message;
    }

    public static SendMessage sendSearchPlaces(Context context, List<PlacesDTO> placesDTOList, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(context.getUserData().getChatId().toString());
        message.setReplyMarkup(ButtonHandlerRef.getMessageFromKeyboardPlaces(placesDTOList));
        message.setText(text);

        return message;
    }

    public static AnswerCallbackQuery sendSubConfirmation(Context context, String text) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(context.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(true);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }

    public static AnswerCallbackQuery sendErrorSearchResult(Context context, String text) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(context.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(true);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }

    public static BotApiMethod<?> sendSubscripList(Context context, List<UserSubscription> userSubscriptionList, int page) {
        SendMessage message = new SendMessage();
        EditMessageText editMessageText = new EditMessageText();

        if (page == 0) {
            message.setChatId(context.getUserData().getChatId().toString());
            message.setText(textRepresentation(context, userSubscriptionList, page));
            message.setReplyMarkup(ButtonHandlerRef.getMessageFromKeyboardSubList(userSubscriptionList));
            return message;
        } else {
            Integer messageID = context.getCallbackQuery().getMessage().getMessageId();
            editMessageText.setChatId(context.getUserData().getChatId().toString());
            editMessageText.setMessageId(messageID);
            editMessageText.setText(textRepresentation(context, userSubscriptionList, page));
            editMessageText.setReplyMarkup(ButtonHandlerRef.getMessageFromKeyboardSubList(userSubscriptionList));

            return editMessageText;
        }
    }


    public static AnswerCallbackQuery sendSubDeleting(Context context, String text) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(context.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(true);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }

    public static String textRepresentation(Context context, List<UserSubscription> userSubscriptionList,int page){
        String text="";
        if (userSubscriptionList.get(page).getUserFlightData().getInboundPartialDate() == null) {
            text= "-------------FLIGHT INFO-------------" + "\n" +
                    "\n   Origin place: " + userSubscriptionList.get(page).getUserFlightData().getOriginPlace() +
                    "\n   Destination place: " + userSubscriptionList.get(page).getUserFlightData().getDestinationPlace() + "\n" +
                    "\n   Outbound partial date: " + userSubscriptionList.get(page).getUserFlightData().getOutboundPartialDate() +
                    "\n   Min price: " + userSubscriptionList.get(page).getSkyScannerResponseQuotes().getQuotes().get(0).getMinPrice() +
                    userSubscriptionList.get(page).getSkyScannerResponseQuotes().getCurrencies().get(0).getSymbol() + "\n" +
                    "\n   Carrier: " + userSubscriptionList.get(page).getSkyScannerResponseQuotes().getCarriers() + "\n" +
                    "\n /Delete_"+page;
        } else {
            text = "-------------FLIGHT INFO-------------" + "\n" +
                    "\n   Origin place: " + userSubscriptionList.get(page).getUserFlightData().getOriginPlace() +
                    "\n   Destination place: " + userSubscriptionList.get(page).getUserFlightData().getDestinationPlace() + "\n" +
                    "\n   Outbound partial date: " + userSubscriptionList.get(page).getUserFlightData().getOutboundPartialDate() +
//                "\n   Min price: " + userSubscriptionList.get(0).getSkyScannerResponseDates().getDates().getInboundDates().get(0).getPrice() +
//                userSubscriptionList.get(0).getSkyScannerResponseDates().getCurrencies().get(0).getSymbol() + "\n" +
                    "\n   Inbound partial date: " + userSubscriptionList.get(page).getUserFlightData().getInboundPartialDate() +
//                "\n   Min price: " + userSubscriptionList.get(0).getSkyScannerResponseDates().getDates().getInboundDates().get(0).getPrice() +
//                userSubscriptionList.get(0).getSkyScannerResponseDates().getCurrencies().get(0).getSymbol() + "\n" +
                    "\n   Min price: " + userSubscriptionList.get(page).getSkyScannerResponseDates().getQuotes().get(0).getMinPrice() +
                    userSubscriptionList.get(page).getSkyScannerResponseDates().getCurrencies().get(0).getSymbol() + "\n" +
                    "\n   Carrier: " + userSubscriptionList.get(page).getSkyScannerResponseDates().getCarriers() + "\n" +
                    "\n /Delete_"+page;
        }
        return text;
    }

}
