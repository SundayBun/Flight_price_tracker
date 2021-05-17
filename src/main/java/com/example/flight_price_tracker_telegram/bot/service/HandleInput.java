package com.example.flight_price_tracker_telegram.bot.service;


import com.example.flight_price_tracker_telegram.bot.BotStateContextRepo;
import com.example.flight_price_tracker_telegram.model.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.model.service.ILocalisationClient;
import com.example.flight_price_tracker_telegram.model.service.LocalisationClientImpl;

import java.util.List;
import java.util.stream.Collectors;

public class HandleInput {

    public static List<CountryDTO> country(BotStateContextRepo context){

        ILocalisationClient localisationClient = new LocalisationClientImpl();
        List<CountryDTO> countriesList = localisationClient.retrieveCountries(context.getUserData().getLocale());

        if (countriesList != null) {
          return countriesList.stream().filter(x -> x.getName().toLowerCase().startsWith(context.getInput().toLowerCase()))
                  .collect(Collectors.toList());
        }
        return null;
    }
}
