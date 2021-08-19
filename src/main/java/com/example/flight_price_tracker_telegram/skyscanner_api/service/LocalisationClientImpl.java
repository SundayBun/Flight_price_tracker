package com.example.flight_price_tracker_telegram.skyscanner_api.service;

import com.example.flight_price_tracker_telegram.skyscanner_api.dto.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.skyscanner_api.dto.localisation.CurrencyDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class LocalisationClientImpl implements ILocalisationClient {

    // @Autowired private IUniRestService uniRestService;


    @Override
    public List<CountryDTO> retrieveCountries(String locale) {

        IUniRestService uniRestService = new UniRestServiceImpl();

        HttpResponse<JsonNode> response = uniRestService.get(String.format(UniRestServiceImpl.COUNTRIES_FORMAT, locale.replaceAll(" ", "")));

        if (response.getStatus() != HttpStatus.SC_OK) {
            return null;
        }
        String jsonList = response.getBody().getObject().get(UniRestServiceImpl.COUNTRIES_KEY).toString();

        return UniRestServiceImpl.readValue(jsonList, new TypeReference<List<CountryDTO>>() {
        });
    }

    @Override
    public List<CurrencyDTO> retrieveCurrencies() {

        IUniRestService uniRestService = new UniRestServiceImpl();
        HttpResponse<JsonNode> response = uniRestService.get(String.format(UniRestServiceImpl.CURRENCIES_FORMAT));

        if (response.getStatus() != HttpStatus.SC_OK) {
            return null;
        }

        String jsonList = response.getBody().getObject().get(UniRestServiceImpl.CURRENCIES_KEY).toString();

        return UniRestServiceImpl.readValue(jsonList, new TypeReference<List<CurrencyDTO>>() {
        });

    }
}
