package com.example.flight_price_tracker_telegram.scheduler;

import com.example.flight_price_tracker_telegram.bot.service.LocaleMessageService;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import com.example.flight_price_tracker_telegram.skyscanner_api.service.FlightPriceClientImpl;
import com.example.flight_price_tracker_telegram.skyscanner_api.service.FlightPriceDateClientImpl;
import com.example.flight_price_tracker_telegram.skyscanner_api.service.IFlightPriceClient;
import com.example.flight_price_tracker_telegram.skyscanner_api.service.IFlightPriceDateClient;
import com.example.flight_price_tracker_telegram.repository.SubscriptionScheduler;
import com.example.flight_price_tracker_telegram.repository.entity.UserSubscription;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;

import java.util.List;
import java.util.Locale;

@PropertySource("classpath:application.properties")
@Slf4j
@Component
public class SchedulerTasks {

    @Value("${telegrambot.botToken}")
    private String botToken;

    @Autowired
    LocaleMessageService localeMessageService;

    @Autowired
    SubscriptionScheduler repository;

    private static final long ONE_HOUR = 1000 * 60 * 60;

    @Scheduled(fixedRate = ONE_HOUR)
    public void renewSubscript() {
        log.debug("recount minPrice Started");

        IFlightPriceDateClient priceDateClient = new FlightPriceDateClientImpl();
        IFlightPriceClient priceClient = new FlightPriceClientImpl();

        List<UserSubscription> userSubscriptionList = repository.findAll();

        userSubscriptionList.forEach(x -> {
            if (x.getUserFlightData().getInboundPartialDate() != null) {
                x.setSkyScannerResponseDates(priceDateClient.browseQuotes(x.getUserData(), x.getUserFlightData()));
                localeMessageService.setLocale(Locale.forLanguageTag(x.getUserData().getLocale()));
                sendMessageToBot(x, false);
                x.setMinPrice(x.getSkyScannerResponseDates().getQuotes().get(0).getMinPrice());

            } else {
                x.setSkyScannerResponseQuotes(priceClient.browseQuotes(x.getUserData(), x.getUserFlightData()));
                localeMessageService.setLocale(Locale.forLanguageTag(x.getUserData().getLocale()));
                sendMessageToBot(x, true);
                x.setMinPrice(x.getSkyScannerResponseQuotes().getQuotes().get(0).getMinPrice());

            }
            repository.save(x);
            log.debug("recount getSkyScannerResponse finished");
        });

        log.debug("recount minPrice finished");
    }

    private void sendMessageToBot(UserSubscription userSubscription, boolean oneWay) {
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&parse_mode=MarkdownV2&text=%s";


        String textPriceIncreasing = localeMessageService.getMessage("scheduler.textPriceIncreasing", Emojis.SCREAM); //Emojis.SCREAM + " PRICE INCREASED";
        String textPriceDrop = localeMessageService.getMessage("scheduler.textPriceDrop", Emojis.DANCER); //Emojis.DANCER + " PRICE DROPPED";

        if (!oneWay) {
            String textDates = localeMessageService.getMessage("scheduler.textDates",
                    ResponseMessage.getPlaceNameFromDTO(userSubscription.getSkyScannerResponseDates().getPlaces(), userSubscription.getUserFlightData().getOriginPlace()),
                    ResponseMessage.getPlaceNameFromDTO(userSubscription.getSkyScannerResponseDates().getPlaces(), userSubscription.getUserFlightData().getDestinationPlace()),
                    ResponseMessage.getDate(userSubscription.getSkyScannerResponseDates().getDates().getOutboundDates().get(0).getPartialDate(), userSubscription.getUserData().getLocale()).replaceAll("\\.", "+"),
                    ResponseMessage.getDate(userSubscription.getSkyScannerResponseDates().getDates().getInboundDates().get(0).getPartialDate(), userSubscription.getUserData().getLocale()).replaceAll("\\.", "+"),
                    userSubscription.getMinPrice().toString(),
                    userSubscription.getSkyScannerResponseDates().getCurrencies().get(0).getSymbol(),
                    userSubscription.getSkyScannerResponseDates().getQuotes().get(0).getMinPrice().toString(),
                    userSubscription.getSkyScannerResponseDates().getCurrencies().get(0).getSymbol());

            if (userSubscription.getMinPrice() > userSubscription.getSkyScannerResponseDates().getQuotes().get(0).getMinPrice()) {
                String text = textPriceDrop + " " + textDates;
                urlString = String.format(urlString, botToken, userSubscription.getChatId(), text.replaceAll("-", "+").replaceAll(" ", "+"));
            } else if (userSubscription.getMinPrice() < userSubscription.getSkyScannerResponseDates().getQuotes().get(0).getMinPrice()) {
                String text = textPriceIncreasing + " " + textDates;
                urlString = String.format(urlString, botToken, userSubscription.getChatId(), text.replaceAll("-", "+").replaceAll(" ", "+"));
            } else return;

        } else {

            String textQuotes = localeMessageService.getMessage("scheduler.textQuotes",
                    ResponseMessage.getPlaceNameFromDTO(userSubscription.getSkyScannerResponseQuotes().getPlaces(), userSubscription.getUserFlightData().getOriginPlace()),
                    ResponseMessage.getPlaceNameFromDTO(userSubscription.getSkyScannerResponseQuotes().getPlaces(), userSubscription.getUserFlightData().getDestinationPlace()),
                    ResponseMessage.getDate(userSubscription.getUserFlightData().getOutboundPartialDate(), userSubscription.getUserData().getLocale()).replaceAll("\\.", "+"),
                    userSubscription.getMinPrice().toString(),
                    userSubscription.getSkyScannerResponseQuotes().getCurrencies().get(0).getSymbol(),
                    userSubscription.getSkyScannerResponseQuotes().getQuotes().get(0).getMinPrice().toString(),
                    userSubscription.getSkyScannerResponseQuotes().getCurrencies().get(0).getSymbol());

            if (userSubscription.getMinPrice() > userSubscription.getSkyScannerResponseQuotes().getQuotes().get(0).getMinPrice()) {
                String text = textPriceDrop + " " + textQuotes;
                urlString = String.format(urlString, botToken, userSubscription.getChatId(), text.replaceAll("-", "+").replaceAll(" ", "+"));
            } else if (userSubscription.getMinPrice() < userSubscription.getSkyScannerResponseQuotes().getQuotes().get(0).getMinPrice()) {
                String text = textPriceIncreasing + " " + textQuotes;
                urlString = String.format(urlString, botToken, userSubscription.getChatId(), text.replaceAll("-", "+").replaceAll(" ", "+"));
            } else return;
        }

        HttpResponse<JsonNode> response = null;

        try {
            response = Unirest.post(urlString).asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        assert response != null;

        log.info("Unirest response status {}", response.getStatus());
        log.info("Unirest response body {}", response.getBody());
    }

}

