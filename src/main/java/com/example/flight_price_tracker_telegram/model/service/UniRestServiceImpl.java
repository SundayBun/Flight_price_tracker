package com.example.flight_price_tracker_telegram.model.service;

import com.example.flight_price_tracker_telegram.model.exception.FlightClientException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Сервис, который работает с REST запросами
 */


@Service
@Slf4j
@PropertySource("classpath:/static/x-rapidAPI-key.properties")
public class UniRestServiceImpl implements IUniRestService {

    public static final String HOST = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com";

    public static final String PLACES_FORMAT = "/apiservices/autosuggest/v1.0/%s/%s/%s/?query=%s";
    public static final String CURRENCIES_FORMAT = "/apiservices/reference/v1.0/currencies";
    public static final String COUNTRIES_FORMAT = "/apiservices/reference/v1.0/countries/%s";

    public static final String PLACES_KEY = "Places";
    public static final String CURRENCIES_KEY = "Currencies";
    public static final String COUNTRIES_KEY = "Countries";

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Value("${X-RapidAPI-Key}")
    private String xRapidApiKey;

    @Override
    public HttpResponse<JsonNode> get(String path) {

        HttpResponse<JsonNode> response = null;

        try {
            response = Unirest.get(HOST + path)
                    .header("x-rapidapi-key", "b37de29cabmshc625f87b4381cb6p1a3873jsndc36ffc88738")//""+ xRapidApiKey)
                    .header("x-rapidapi-host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com")
                    .asJson();
        } catch (UnirestException e) {
            throw new FlightClientException(String.format("Request failed, path=%s", HOST + path), e);
        }

        log.info("Response from Get request, on path={}, statusCode={}, response={}"
                , path, response.getStatus(), response.getBody().toString());

        return response;
    }

    static  <T> T readValue(String resultAsString, TypeReference<T> valueTypeRef) {
        try {
           return objectMapper.readValue(resultAsString, valueTypeRef);
        } catch (IOException e) {
            throw new FlightClientException("Object Mapping failure.", e);
        }
    }
}
