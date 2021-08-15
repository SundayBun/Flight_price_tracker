package com.example.flight_price_tracker_telegram.skyscanner_api.service;

import com.example.flight_price_tracker_telegram.skyscanner_api.dto.browse.*;
import com.example.flight_price_tracker_telegram.skyscanner_api.dto.localisation.CurrencyDTO;
import com.example.flight_price_tracker_telegram.repository.entity.UserData;
import com.example.flight_price_tracker_telegram.repository.entity.UserFlightData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.apache.http.HttpStatus;

import java.util.List;

public class FlightPriceDateClientImpl implements IFlightPriceDateClient {

    public static final String BROWSE_DATES_FORMAT = "/apiservices/browsedates/v1.0/%s/%s/%s/%s/%s/%s/%s";

    public static final String QUOTES_KEY = "Quotes";
    public static final String ROUTES_KEY = "Routes";
    public static final String DATES_KEY = "Dates";
    public static final String CARRIERS_KEY = "Carriers";
    public static final String VALIDATIONS_KEY = "ERROR";

    @Override
    public BrowseDatesDTO browseQuotes(UserData userData, UserFlightData userFlightData) {

        IUniRestService uniRestService=new UniRestServiceImpl();
        HttpResponse<JsonNode> response=null;

            response = uniRestService.get(String.format(BROWSE_DATES_FORMAT, userData.getCountry(), userData.getCurrency(),
                    userData.getLocale(), userFlightData.getOriginPlace(), userFlightData.getDestinationPlace(),
                    userFlightData.getOutboundPartialDate(), userFlightData.getInboundPartialDate()));

            return mapToObject(response);

    }

    private BrowseDatesDTO mapToObject(HttpResponse<JsonNode> response) {
        if (response.getStatus() == HttpStatus.SC_OK) {
            BrowseDatesDTO flightDatesDTO = new BrowseDatesDTO();

            flightDatesDTO.setQuotes(UniRestServiceImpl.readValue(response.getBody().getObject().get(QUOTES_KEY).toString(),
                    new TypeReference<List<QuotesDTO>>() {
                    }));
            flightDatesDTO.setCarriers(UniRestServiceImpl.readValue(response.getBody().getObject().get(CARRIERS_KEY).toString(),
                    new TypeReference<List<CarriersDTO>>() {
                    }));
            flightDatesDTO.setPlaces(UniRestServiceImpl.readValue(response.getBody().getObject().get(UniRestServiceImpl.PLACES_KEY).toString(),
                    new TypeReference<List<BrowsePlacesDTO>>() {
                    }));
            flightDatesDTO.setCurrencies(UniRestServiceImpl.readValue(response.getBody().getObject().get(UniRestServiceImpl.CURRENCIES_KEY).toString(),
                    new TypeReference<List<CurrencyDTO>>() {
                    }));
            flightDatesDTO.setDates(UniRestServiceImpl.readValue(response.getBody().getObject().get(DATES_KEY).toString(),
                    new TypeReference<DatesDTO>() {
                    }));

            return flightDatesDTO;
        }
        return null;
//        throw new FlightClientException(String.format("There are validation errors. statusCode = %s", response.getStatus()),
//                UniRestServiceImpl.readValue(response.getBody().getObject().get(VALIDATIONS_KEY).toString(),
//                        new TypeReference<List<ValidationErrorDTO>>() {
//                        }));
    }
}
