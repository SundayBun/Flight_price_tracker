package com.example.flight_price_tracker_telegram.bot.service;



import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.skyscanner_api.dto.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.skyscanner_api.dto.places.PlacesDTO;
import com.example.flight_price_tracker_telegram.skyscanner_api.service.ILocalisationClient;
import com.example.flight_price_tracker_telegram.skyscanner_api.service.IPlacesClient;
import com.example.flight_price_tracker_telegram.skyscanner_api.service.LocalisationClientImpl;
import com.example.flight_price_tracker_telegram.skyscanner_api.service.PlacesClientImpl;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HandleInput {

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

    public static String formatDate(String date){
        DateTimeFormatter yymmdd=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter ddmmyy=DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate= LocalDate.parse(date,ddmmyy);
        return yymmdd.format(localDate);
    }
}

