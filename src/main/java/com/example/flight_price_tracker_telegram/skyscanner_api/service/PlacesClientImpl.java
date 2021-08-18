package com.example.flight_price_tracker_telegram.skyscanner_api.service;

import com.example.flight_price_tracker_telegram.skyscanner_api.dto.places.PlacesDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlacesClientImpl implements IPlacesClient {

//    @Autowired
//    private IUniRestService uniRestService;


    @Override
    public List<PlacesDTO> retrievePlaces(String query, String country, String currency, String locale) {
        IUniRestService uniRestService=new UniRestServiceImpl();

        HttpResponse<JsonNode> response=uniRestService.get(String.format(UniRestServiceImpl.PLACES_FORMAT,country,currency,locale,query.replaceAll(" ","")));

        if(response.getStatus()!= HttpStatus.SC_OK) {
            return null;
        }
        String jsonList=response.getBody().getObject().get(UniRestServiceImpl.PLACES_KEY).toString();
        return UniRestServiceImpl.readValue(jsonList,new TypeReference<List<PlacesDTO>>(){});
    }
}
