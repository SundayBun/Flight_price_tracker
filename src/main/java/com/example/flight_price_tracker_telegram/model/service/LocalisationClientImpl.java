package com.example.flight_price_tracker_telegram.model.service;

import com.example.flight_price_tracker_telegram.model.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.model.localisation.CurrencyDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class LocalisationClientImpl implements ILocalisationClient{

//    @Autowired
//    private IUniRestService uniRestService;


    @Override
    public List<CountryDTO> retrieveCountries(String locale) {

        IUniRestService uniRestService=new UniRestServiceImpl();

        HttpResponse<JsonNode> response=uniRestService.get(String.format(UniRestServiceImpl.COUNTRIES_FORMAT,locale));

        if(response.getStatus()!= HttpStatus.SC_OK) {
            return null;
        }
        String jsonList=response.getBody().getObject().get(UniRestServiceImpl.COUNTRIES_KEY).toString();

        return UniRestServiceImpl.readValue(jsonList,new TypeReference<List<CountryDTO>>(){});
    }

    @Override
    public List<CurrencyDTO> retrieveCurrencies() {

        IUniRestService uniRestService=new UniRestServiceImpl();
        HttpResponse<JsonNode> response=uniRestService.get(String.format(UniRestServiceImpl.CURRENCIES_FORMAT));

        if(response.getStatus()!= HttpStatus.SC_OK) {
            return null;
        }

        String jsonList=response.getBody().getObject().get(UniRestServiceImpl.CURRENCIES_KEY).toString();

        return UniRestServiceImpl.readValue(jsonList,new TypeReference<List<CurrencyDTO>>(){});

    }
}
