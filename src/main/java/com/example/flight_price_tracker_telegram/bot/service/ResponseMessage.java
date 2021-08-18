package com.example.flight_price_tracker_telegram.bot.service;


import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.skyscanner_api.dto.browse.BrowsePlacesDTO;
import com.example.flight_price_tracker_telegram.skyscanner_api.dto.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.skyscanner_api.dto.places.PlacesDTO;
import com.example.flight_price_tracker_telegram.repository.entity.UserSubscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class ResponseMessage {


    public static SendMessage sendMessage(Context context, String messageText, String buttonText) {

        SendMessage message = new SendMessage();
        message.setChatId(context.getUserData().getChatId().toString());

        if (!context.getState().isQueryResponse()) {
            message.setText(messageText);
        } else {
            message.setText(messageText);
            message.setReplyMarkup(ButtonHandler.getMessageFromKeyboard(context, buttonText));
        }
        return message;
    }

    public static SendMessage sendMessageOriginPlace(Context context, String messageText, String... buttonTextArgs) {
        SendMessage message = new SendMessage();
        message.setChatId(context.getUserData().getChatId().toString());
        message.setText(messageText);
        message.setReplyMarkup(ButtonHandler.getMainMenuKeyboard(buttonTextArgs[0], buttonTextArgs[1], buttonTextArgs[2]));
        return message;
    }


    public static SendMessage sendSearchResult(Context context, String messageText, String buttonText) {

        SendMessage message = new SendMessage();
        message.setChatId(context.getUserFlightData().getChatId().toString());
        message.setText(messageText);
        message.setReplyMarkup(ButtonHandler.getMessageFromKeyboard(context, buttonText));

        log.info("sendSearchResult setText={}", message.getText());

        return message;
    }

    public static SendMessage sendSearchCountry(Context context, List<CountryDTO> countryDTOList, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(context.getUserData().getChatId().toString());
        message.setReplyMarkup(ButtonHandler.getMessageFromKeyboardCountry(countryDTOList));
        message.setText(text);

        return message;
    }

    public static SendMessage sendSearchPlaces(Context context, List<PlacesDTO> placesDTOList, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(context.getUserData().getChatId().toString());
        message.setReplyMarkup(ButtonHandler.getMessageFromKeyboardPlaces(placesDTOList));
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

    public static BotApiMethod<?> sendSubscripList(Context context, List<UserSubscription> userSubscriptionList, int page, String text) {
        SendMessage message = new SendMessage();
        EditMessageText editMessageText = new EditMessageText();

        if (page == 0) {
            message.setChatId(context.getUserData().getChatId().toString());
            message.setText(text);
            message.setReplyMarkup(ButtonHandler.getMessageFromKeyboardSubList(userSubscriptionList));
            return message;
        } else {
            Integer messageID = context.getCallbackQuery().getMessage().getMessageId();
            editMessageText.setChatId(context.getUserData().getChatId().toString());
            editMessageText.setMessageId(messageID);
            editMessageText.setText(text);
            editMessageText.setReplyMarkup(ButtonHandler.getMessageFromKeyboardSubList(userSubscriptionList));

            return editMessageText;
        }
    }

    public static AnswerCallbackQuery sendSubDeleting(Context context, String text) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(context.getUserData().getId());
        answerCallbackQuery.setShowAlert(true);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }

    public static String getPlaceNameFromDTO(List<BrowsePlacesDTO> places, String place) {
        String cutPlace = place.substring(0, place.lastIndexOf("-"));
        BrowsePlacesDTO browsePlacesDTO = places.stream().filter(x -> x.getCityId().startsWith(cutPlace)).findFirst().orElse(null);
        if (browsePlacesDTO != null) {
            return browsePlacesDTO.getCityName();
        }
        return place;
    }

    public static String getDate(String date, String locale) {
        Locale localeRu = new Locale("ru", "Ru");
        DateTimeFormatter yymmdd = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (locale.equals("ru-RU")) {
            DateTimeFormatter ddmmyyRu = DateTimeFormatter.ofPattern("dd MMM yyyy", localeRu);
            LocalDate localDate = LocalDate.parse(date, yymmdd);
            return ddmmyyRu.format(localDate);
        }

        DateTimeFormatter ddmmyy = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        LocalDate localDate = LocalDate.parse(date, yymmdd);
        return ddmmyy.format(localDate);

    }
//     private static Locale localeRu(String date){
//         Locale locale = new Locale("ru","Ru");
//         DateTimeFormatter ddmmyy=DateTimeFormatter.ofPattern("dd MMM yyyy",locale);
//         LocalDate localDate= LocalDate.parse(date);
//         return ddmmyy.format(localDate);
//         DateFormatSymbols dfs = DateFormatSymbols.getInstance(locale);
//         String[] months = {
//                 "января", "февраля", "марта", "апреля", "мая", "июня",
//                 "июля", "августа", "сентября", "октября", "ноября", "декабря"};
//         String[] shortMonths = {
//                 "янв", "фев", "мар", "апр", "май", "июн",
//                 "июл", "авг", "сен", "окт", "ноя", "дек"};
//         dfs.setMonths(months);
//         dfs.setShortMonths(shortMonths);
    //    }

}
