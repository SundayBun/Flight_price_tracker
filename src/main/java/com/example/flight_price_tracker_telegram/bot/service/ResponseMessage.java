package com.example.flight_price_tracker_telegram.bot.service;

import com.example.flight_price_tracker_telegram.bot.BotState;

import com.example.flight_price_tracker_telegram.bot.BotStateContext;
import com.example.flight_price_tracker_telegram.model.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.model.places.PlacesDTO;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.List;

@Slf4j
public class ResponseMessage {

    public static SendMessage sendMessage(BotStateContext context, BotState state, boolean queryResponse, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(context.getUserData().getChatId().toString());

        if (!queryResponse) {
            message.setText(text);
            if (state == BotState.ORIGIN_PLACE_TEXT) {
                message.setReplyMarkup(ButtonHandler.getMainMenuKeyboard());
            }
        } else {
            message.setText(text);
            message.setReplyMarkup(ButtonHandler.getMessageFromKeyboard(state));
        }
        return message;

    }

    public static SendMessage sendSearchResult(BotStateContext context, BotState state) {

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

        message.setReplyMarkup(ButtonHandler.getMessageFromKeyboard(state));

        log.info("sendSearchResult setText={}", message.getText());
        return message;
    }

    public static SendMessage sendSearchCountry(BotStateContext context, List<CountryDTO> countryDTOList, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(context.getUserData().getChatId().toString());
        message.setReplyMarkup(ButtonHandler.getMessageFromKeyboardCountry(countryDTOList));
        message.setText(text);

        return message;
    }

    public static SendMessage sendSearchPlaces(BotStateContext context, List<PlacesDTO> placesDTOList, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(context.getUserData().getChatId().toString());
        message.setReplyMarkup(ButtonHandler.getMessageFromKeyboardPlaces(placesDTOList));
        message.setText(text);

        return message;
    }

    public static AnswerCallbackQuery sendSubConfirmation(BotStateContext context, String text) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(context.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(true);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }

    public static AnswerCallbackQuery sendErrorSearchResult(BotStateContext context, String text) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(context.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(true);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }

    public static SendMessage sendSubscripList(BotStateContext context, List<UserSubscription> userSubscriptionList) {

        SendMessage message = new SendMessage();
        message.setChatId(context.getUserData().getChatId().toString());

            if (userSubscriptionList.get(0).getUserFlightData().getInboundPartialDate() == null) {
                String textOneWay = "-------------FLIGHT INFO-------------" + "\n" +
                        "\n   Origin place: " + userSubscriptionList.get(0).getUserFlightData().getOriginPlace() +
                        "\n   Destination place: " + userSubscriptionList.get(0).getUserFlightData().getDestinationPlace() + "\n" +
                        "\n   Outbound partial date: " + userSubscriptionList.get(0).getUserFlightData().getOutboundPartialDate() +
                        "\n   Min price: " + userSubscriptionList.get(0).getSkyScannerResponseQuotes().getQuotes().get(0).getMinPrice() +
                        userSubscriptionList.get(0).getSkyScannerResponseQuotes().getCurrencies().get(0).getSymbol() + "\n" +
                        "\n   Carrier: " + userSubscriptionList.get(0).getSkyScannerResponseQuotes().getCarriers() + "\n" +
                        "\n /Delete_0";

                message.setText(textOneWay);
            } else {
                String textTwoWays = "-------------FLIGHT INFO-------------" + "\n" +
                        "\n   Origin place: " + userSubscriptionList.get(0).getUserFlightData().getOriginPlace() +
                        "\n   Destination place: " + userSubscriptionList.get(0).getUserFlightData().getDestinationPlace() + "\n" +
                        "\n   Outbound partial date: " + userSubscriptionList.get(0).getUserFlightData().getOutboundPartialDate() +
//                "\n   Min price: " + userSubscriptionList.get(0).getSkyScannerResponseDates().getDates().getInboundDates().get(0).getPrice() +
//                userSubscriptionList.get(0).getSkyScannerResponseDates().getCurrencies().get(0).getSymbol() + "\n" +
                        "\n   Inbound partial date: " + userSubscriptionList.get(0).getUserFlightData().getInboundPartialDate() +
//                "\n   Min price: " + userSubscriptionList.get(0).getSkyScannerResponseDates().getDates().getInboundDates().get(0).getPrice() +
//                userSubscriptionList.get(0).getSkyScannerResponseDates().getCurrencies().get(0).getSymbol() + "\n" +
                        "\n   Min price: " + userSubscriptionList.get(0).getSkyScannerResponseDates().getQuotes().get(0).getMinPrice() +
                        userSubscriptionList.get(0).getSkyScannerResponseDates().getCurrencies().get(0).getSymbol() + "\n" +
                        "\n   Carrier: " + userSubscriptionList.get(0).getSkyScannerResponseDates().getCarriers() + "\n" +
                        "\n /Delete_0";

                message.setText(textTwoWays);
            }
        message.setReplyMarkup(ButtonHandler.getMessageFromKeyboardSubList(userSubscriptionList));
        return message;
    }

    public static EditMessageText sendSubscripListEdited(BotStateContext context, List<UserSubscription> userSubscriptionList) {
        int index = Integer.parseInt(context.getCallbackQuery().getData());
        EditMessageText editMessageText = new EditMessageText();
        Integer messageID = context.getCallbackQuery().getMessage().getMessageId();

        editMessageText.setChatId(context.getUserData().getChatId().toString());
        editMessageText.setMessageId(messageID);

        if (userSubscriptionList.get(index).getUserFlightData().getInboundPartialDate()== null) {
            String texOneWay = "-------------FLIGHT INFO-------------" + "\n" +
                    "\n   Origin place: " + userSubscriptionList.get(index).getUserFlightData().getOriginPlace() +
                    "\n   Destination place: " + userSubscriptionList.get(index).getUserFlightData().getDestinationPlace() +
                    "\n   Outbound partial date: " + userSubscriptionList.get(index).getUserFlightData().getOutboundPartialDate() +
                    "\n   Min price: " + userSubscriptionList.get(index).getSkyScannerResponseQuotes().getQuotes().get(0).getMinPrice() +
                    userSubscriptionList.get(index).getSkyScannerResponseQuotes().getCurrencies().get(0).getSymbol() + "\n" +
                    "\n   Carrier: " + userSubscriptionList.get(index).getSkyScannerResponseQuotes().getCarriers() + "\n" +
                    "\n /Delete_" + context.getCallbackQuery().getData();
            editMessageText.setText(texOneWay);
        } else {
            String texTwoWays = "-------------FLIGHT INFO-------------" + "\n" +
                    "\n   Origin place: " + userSubscriptionList.get(index).getUserFlightData().getOriginPlace() +
                    "\n   Destination place: " + userSubscriptionList.get(index).getUserFlightData().getDestinationPlace() +
                    "\n   Outbound partial date: " + userSubscriptionList.get(index).getUserFlightData().getOutboundPartialDate() +
//                "\n   Min price: " + userSubscriptionList.get(index).getSkyScannerResponseDates().getDates().getInboundDates().get(0).getPrice() +
//                userSubscriptionList.get(0).getSkyScannerResponseDates().getCurrencies().get(0).getSymbol() +
                    "\n   Inbound partial date: " + userSubscriptionList.get(index).getUserFlightData().getInboundPartialDate() +
//                "\n   Min price: " + userSubscriptionList.get(index).getSkyScannerResponseDates().getDates().getInboundDates().get(0).getPrice() +
//                userSubscriptionList.get(index).getSkyScannerResponseDates().getCurrencies().get(0).getSymbol() +
                    "\n   Min price: " + userSubscriptionList.get(index).getSkyScannerResponseDates().getQuotes().get(0).getMinPrice() +
                    userSubscriptionList.get(index).getSkyScannerResponseDates().getCurrencies().get(0).getSymbol() + "\n" +
                    "\n   Carrier: " + userSubscriptionList.get(index).getSkyScannerResponseDates().getCarriers() + "\n" +
                    "\n /Delete_" + context.getCallbackQuery().getData();

            editMessageText.setText(texTwoWays);
        }

        editMessageText.setReplyMarkup(ButtonHandler.getMessageFromKeyboardSubList(userSubscriptionList));

        return editMessageText;

    }

    public static AnswerCallbackQuery sendSubDeleting(BotStateContext context, String text) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(context.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(true);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }

}
