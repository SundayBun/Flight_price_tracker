package com.example.flight_price_tracker_telegram.repository;


import com.example.flight_price_tracker_telegram.model.browse.FlightPricesDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSubscriptionDataService {

    ISubscriptionRepositoryUserData userDataRepository;
    ISubscriptionRepositoryFlightData userFlightDataIml;

    public UserSubscriptionDataService(ISubscriptionRepositoryUserData userDataRepository,ISubscriptionRepositoryFlightData userFlightData) {
        this.userDataRepository = userDataRepository;
        this.userFlightDataIml=userFlightData;
    }

    public UserData findByChatId(long chatId) {
        return userDataRepository.findByChatId(chatId);
    }

    public UserFlightData findByChatID(long chatId) {
        return userFlightDataIml.findByChatId(chatId).stream().findFirst().orElse(null);
    }

    public void saveUserData(UserData userData) {
         userDataRepository.save(userData);
    }

    public void saveUserFlightData(UserFlightData userFlightData) {
        userFlightDataIml.save(userFlightData);
    }

    public UserFlightData findBySkyScannerResponse (FlightPricesDTO response,UserFlightData userFlightData){
     return  userFlightDataIml.findByChatId(userFlightData.getChatId()).stream()
             .filter(x->x.getSkyScannerResponse().equals(response)).findFirst().get();
    }
}
