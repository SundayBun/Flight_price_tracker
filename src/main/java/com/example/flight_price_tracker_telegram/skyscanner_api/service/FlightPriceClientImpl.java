package com.example.flight_price_tracker_telegram.skyscanner_api.service;

import com.example.flight_price_tracker_telegram.skyscanner_api.dto.browse.BrowsePlacesDTO;
import com.example.flight_price_tracker_telegram.skyscanner_api.dto.browse.CarriersDTO;
import com.example.flight_price_tracker_telegram.skyscanner_api.dto.browse.FlightPricesDTO;
import com.example.flight_price_tracker_telegram.skyscanner_api.dto.browse.QuotesDTO;
import com.example.flight_price_tracker_telegram.skyscanner_api.dto.localisation.CurrencyDTO;
import com.example.flight_price_tracker_telegram.repository.entity.UserData;
import com.example.flight_price_tracker_telegram.repository.entity.UserFlightData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlightPriceClientImpl implements IFlightPriceClient {

    public static final String BROWSE_QUOTES_FORMAT = "/apiservices/browsequotes/v1.0/%s/%s/%s/%s/%s/%s";
    public static final String OPTIONAL_BROWSE_QUOTES_FORMAT = BROWSE_QUOTES_FORMAT + "?inboundpartialdate=%s";

    public static final String QUOTES_KEY = "Quotes";
    public static final String ROUTES_KEY = "Routes";
    public static final String DATES_KEY = "Dates";
    public static final String CARRIERS_KEY = "Carriers";
    public static final String VALIDATIONS_KEY = "ERROR";

    @Override
    public FlightPricesDTO browseQuotes(UserData userData, UserFlightData userFlightData) {
        IUniRestService uniRestService = new UniRestServiceImpl();
        HttpResponse<JsonNode> response = null;
        if (userFlightData.getInboundPartialDate() != null) {

            response = uniRestService.get(String.format(OPTIONAL_BROWSE_QUOTES_FORMAT, userData.getCountry(), userData.getCurrency(),
                    userData.getLocale(), userFlightData.getOriginPlace(), userFlightData.getDestinationPlace(),
                    userFlightData.getOutboundPartialDate(), userFlightData.getInboundPartialDate()));

            return mapToObject(response);
        }
        response = uniRestService.get(String.format(BROWSE_QUOTES_FORMAT, userData.getCountry(), userData.getCurrency(),
                userData.getLocale(), userFlightData.getOriginPlace(), userFlightData.getDestinationPlace(),
                userFlightData.getOutboundPartialDate()));
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
        return null;
    }

}
