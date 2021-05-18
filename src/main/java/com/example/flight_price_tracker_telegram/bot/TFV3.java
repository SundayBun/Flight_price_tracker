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
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

@Component
public class TFV3 {

    final String HOST = "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com";
    final String path = "/apiservices/browsequotes/v1.0/US/USD/en-US/SFO-sky/JFK-sky/2021-09-01?inboundpartialdate=2021-12-01";
    HttpResponse<JsonNode> response = null;

    final String QUOTES_KEY = "Quotes";
    final String ROUTES_KEY = "Routes";
    final String DATES_KEY = "Dates";
    final String CARRIERS_KEY = "Carriers";
    final String VALIDATIONS_KEY = "ValidationErrors";



    public SendMessage handleUpdate(Update update, FlightPriceTrackerTelegramBot bot) {
        String chatID=update.getMessage().getChatId().toString();
        String text=update.getMessage().getText();
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.setText(browseQuote().toString());
        return sendMessage;
    }

        public  FlightPricesDTO browseQuote(){

        try {
            response = Unirest.get("https://" + HOST + path)
                    .header("x-rapidapi-key", "b37de29cabmshc625f87b4381cb6p1a3873jsndc36ffc88738")//""+ xRapidApiKey)
                    .header("x-rapidapi-host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com")
                    .asJson();
        } catch (
                UnirestException e) {
            throw new FlightClientException(String.format("Request failed, path=%s", HOST + path), e);
        }

       return mapToObject(response);
    }

    private FlightPricesDTO mapToObject(HttpResponse<JsonNode> response) {
        if (response.getStatus() == HttpStatus.SC_OK) {
            FlightPricesDTO flightPricesDTO = new FlightPricesDTO();

            flightPricesDTO.setQuotes(UniRestServiceImpl.readValue(response.getBody().getObject().get(QUOTES_KEY).toString(),
                    new TypeReference<List<QuotesDTO>>() {
                    }));
            flightPricesDTO.setCarriers(UniRestServiceImpl.readValue(response.getBody().getObject().get(CARRIERS_KEY).toString(),
                    new TypeReference<List<CarriersDTO>>() {
                    }));
            flightPricesDTO.setPlaces(UniRestServiceImpl.readValue(response.getBody().getObject().get(UniRestServiceImpl.PLACES_KEY).toString(),
                    new TypeReference<List<BrowsePlacesDTO>>() {
                    }));
            flightPricesDTO.setCurrencies(UniRestServiceImpl.readValue(response.getBody().getObject().get(UniRestServiceImpl.CURRENCIES_KEY).toString(),
                    new TypeReference<List<CurrencyDTO>>() {
                    }));
            return flightPricesDTO;
        }
        throw new FlightClientException(String.format("There are validation errors. statusCode = %s", response.getStatus()),
                UniRestServiceImpl.readValue(response.getBody().getObject().get(VALIDATIONS_KEY).toString(),
                        new TypeReference<List<ValidationErrorDTO>>() {
                        }));
    }

}
