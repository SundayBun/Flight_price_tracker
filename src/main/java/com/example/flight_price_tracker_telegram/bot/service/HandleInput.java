package com.example.flight_price_tracker_telegram.bot.service;


import com.example.flight_price_tracker_telegram.bot.BotStateContext;
import com.example.flight_price_tracker_telegram.model.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.model.places.PlacesDTO;
import com.example.flight_price_tracker_telegram.model.service.ILocalisationClient;
import com.example.flight_price_tracker_telegram.model.service.IPlacesClient;
import com.example.flight_price_tracker_telegram.model.service.LocalisationClientImpl;
import com.example.flight_price_tracker_telegram.model.service.PlacesClientImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HandleInput {

    public static List<CountryDTO> country(BotStateContext context){

        ILocalisationClient localisationClient = new LocalisationClientImpl();
        List<CountryDTO> countriesList = localisationClient.retrieveCountries(context.getUserData().getLocale());

        if (countriesList != null) {
          return countriesList.stream().filter(x -> x.getName().toLowerCase().startsWith(context.getInput().toLowerCase()))
                  .collect(Collectors.toList());
        }
        return null;
    }

    public static List<PlacesDTO> places(BotStateContext context){
        IPlacesClient iPlacesClient = new PlacesClientImpl();
        List<PlacesDTO> placesList = iPlacesClient.retrievePlaces(context.getInput(),context.getUserData().getCountry(),
                context.getUserData().getCurrency(),context.getUserData().getLocale());

        return placesList;
    }


}

