package com.example.flight_price_tracker_telegram.bot.refactored.service;



import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.states.StateName;
import com.example.flight_price_tracker_telegram.model.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.model.places.PlacesDTO;
import com.example.flight_price_tracker_telegram.model.service.ILocalisationClient;
import com.example.flight_price_tracker_telegram.model.service.IPlacesClient;
import com.example.flight_price_tracker_telegram.model.service.LocalisationClientImpl;
import com.example.flight_price_tracker_telegram.model.service.PlacesClientImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class HandleInputRef {

    public static List<CountryDTO> country(Context context){

        ILocalisationClient localisationClient = new LocalisationClientImpl();
        List<CountryDTO> countriesList = localisationClient.retrieveCountries(context.getUserData().getLocale());

        if (countriesList != null) {
          return countriesList.stream().filter(x -> x.getName().toLowerCase().startsWith(context.getInput().toLowerCase()))
                  .collect(Collectors.toList());
        }
        return null;
    }

    public static List<PlacesDTO> places(Context context){
        IPlacesClient iPlacesClient = new PlacesClientImpl();
        List<PlacesDTO> placesList = iPlacesClient.retrievePlaces(context.getInput(),context.getUserData().getCountry(),
                context.getUserData().getCurrency(),context.getUserData().getLocale());

        return placesList;
    }
}

