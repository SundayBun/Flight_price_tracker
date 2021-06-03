package com.example.flight_price_tracker_telegram.scheduler;

import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import com.example.flight_price_tracker_telegram.model.browse.BrowseDatesDTO;
import com.example.flight_price_tracker_telegram.model.service.FlightPriceClientImpl;
import com.example.flight_price_tracker_telegram.model.service.FlightPriceDateClientImpl;
import com.example.flight_price_tracker_telegram.model.service.IFlightPriceClient;
import com.example.flight_price_tracker_telegram.model.service.IFlightPriceDateClient;
import com.example.flight_price_tracker_telegram.repository.SubscriptionScheduler;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@PropertySource("classpath:application.properties")
@Slf4j
@Component
public class SchedulerTasks {

    @Value("${telegrambot.botToken}")
    private String botToken;

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
                sendMessageToBot(x, false);
                x.setMinPrice(x.getSkyScannerResponseDates().getQuotes().get(0).getMinPrice());

            } else {
                x.setSkyScannerResponseQuotes(priceClient.browseQuotes(x.getUserData(), x.getUserFlightData()));
                sendMessageToBot(x, true);
                x.setMinPrice(x.getSkyScannerResponseQuotes().getQuotes().get(0).getMinPrice());

            }
            repository.save(x);
            log.debug("recount getSkyScannerResponse finished");
        });

        log.debug("recount minPrice finished");
    }

    public void sendMessageToBot(UserSubscription userSubscription, boolean oneWay) {
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&parse_mode=MarkdownV2&text=%s";


        String textPriceIncreasing = Emojis.SCREAM + " PRICE INCREASED";
        String textPriceDrop = Emojis.DANCER + " PRICE DROPPED";

        if (!oneWay) {
            String textDates = String.format(" from %s to %s      Dates: %s - %s        Price:  ~%s%s~ %s%s",
                    userSubscription.getSkyScannerResponseDates().getPlaces().get(0).getCityName(),
                    userSubscription.getSkyScannerResponseDates().getPlaces().get(1).getCityName(),
                    userSubscription.getSkyScannerResponseDates().getDates().getOutboundDates().get(0).getPartialDate(),
                    userSubscription.getSkyScannerResponseDates().getDates().getInboundDates().get(0).getPartialDate(),
                    userSubscription.getMinPrice(),
                    userSubscription.getSkyScannerResponseDates().getCurrencies().get(0).getSymbol(),
                    userSubscription.getSkyScannerResponseDates().getQuotes().get(0).getMinPrice(),
                    userSubscription.getSkyScannerResponseDates().getCurrencies().get(0).getSymbol());

            if (userSubscription.getMinPrice() > userSubscription.getSkyScannerResponseDates().getQuotes().get(0).getMinPrice()) {
                String text=textPriceDrop+textDates;
                urlString = String.format(urlString, botToken, userSubscription.getChatId(), text.replaceAll("-","+").replaceAll(" ","+"));
            } else if (userSubscription.getMinPrice() < userSubscription.getSkyScannerResponseDates().getQuotes().get(0).getMinPrice()) {
                String text=textPriceIncreasing+textDates;
                urlString = String.format(urlString, botToken, userSubscription.getChatId(), text.replaceAll("-","+").replaceAll(" ","+"));
            } else return;

        } else {
            String textQuotes = String.format(" from %s to %s       Date: %s            Price: ~%s%s~  %s%s",
                    userSubscription.getSkyScannerResponseQuotes().getPlaces().get(0).getCityName(),
                    userSubscription.getSkyScannerResponseQuotes().getPlaces().get(1).getCityName(),
                    userSubscription.getUserFlightData().getOutboundPartialDate(),
                    userSubscription.getMinPrice(),
                    userSubscription.getSkyScannerResponseQuotes().getCurrencies().get(0).getSymbol(),
                    userSubscription.getSkyScannerResponseQuotes().getQuotes().get(0).getMinPrice(),
                   userSubscription.getSkyScannerResponseQuotes().getCurrencies().get(0).getSymbol());

            if (userSubscription.getMinPrice() > userSubscription.getSkyScannerResponseQuotes().getQuotes().get(0).getMinPrice()) {
                String text=textPriceDrop+textQuotes;
                urlString = String.format(urlString, botToken, userSubscription.getChatId(), text.replaceAll("-","+").replaceAll(" ","+"));
            } else if (userSubscription.getMinPrice() < userSubscription.getSkyScannerResponseQuotes().getQuotes().get(0).getMinPrice()) {
                String text=textPriceIncreasing+textQuotes;
                urlString = String.format(urlString, botToken, userSubscription.getChatId(), text.replaceAll("-","+").replaceAll(" ","+"));
            } else return;
        }

        HttpResponse<JsonNode> response = null;

        try {
            response=Unirest.post(urlString).asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        assert response != null;
        log.info("Unirest response status {}",response.getStatus());
        log.info("Unirest response body {}", response.getBody());

    }

 //  @Scheduled(fixedRate = TEN_MINUTES)
    public void renewSubscript2() {
        String text="%7eChecking%7e++"+Emojis.DANCER;
        String url = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&parse_mode=MarkdownV2&text=%s";
        String urlString = String.format(url, botToken, "318658114", text);

        //HttpResponse<JsonNode> response = null;

        try {
            HttpResponse<JsonNode> response = Unirest.post(urlString).asJson();
            log.info("checking {}", response.getStatus());
            log.info("checking {}", response.getBody());
        } catch (UnirestException e) {
            e.printStackTrace();
        }


    }



}

