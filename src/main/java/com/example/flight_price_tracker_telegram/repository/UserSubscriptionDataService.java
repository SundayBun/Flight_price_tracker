package com.example.flight_price_tracker_telegram.repository;


import com.example.flight_price_tracker_telegram.model.browse.FlightPricesDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSubscriptionDataService {

    ISubscriptionRepositoryUserData userDataRepository;
    ISubscriptionRepositoryFlightData userFlightDataIml;
    ISubscriptionRepository subscriptionRepository;

    public UserSubscriptionDataService(ISubscriptionRepositoryUserData userDataRepository, ISubscriptionRepositoryFlightData userFlightData,  ISubscriptionRepository subscriptionRepository) {
        this.userDataRepository = userDataRepository;
        this.userFlightDataIml = userFlightData;
        this.subscriptionRepository=subscriptionRepository;
    }

    public UserData findByChatId(long chatId) {
        return userDataRepository.findByChatId(chatId);
    }

    public UserData findByIdOrChatId(String id, Long chatID) {
       if(userDataRepository.findByChatId(chatID)==null){
           return userDataRepository.findById(id);
       }
        return userDataRepository.findByChatId(chatID);

    }

    public UserFlightData findByFlightData(long chatId, UserFlightData userFlightData) {
        if (userFlightDataIml.findByChatId(chatId) != null) {
            return userFlightDataIml.findByChatId(chatId)
                    .stream().filter(x -> x.equals(userFlightData)).findFirst().orElse(null);
        }
        return null;
    }


    public void saveUserData(UserData userData) {
        userDataRepository.save(userData);
    }

    public void saveUserFlightData(UserFlightData userFlightData) {
        userFlightDataIml.save(userFlightData);
    }


    public UserFlightData findByChatID(long chatId) {
        return userFlightDataIml.findByChatId(chatId)
                .stream().filter(x -> x.getChatId()
                        .equals(chatId)).findFirst().orElse(null);
    }

    public UserFlightData findByIdOrChatID(String id, Long chatID) {
        if(userFlightDataIml.findByChatId(chatID)==null){
            return userFlightDataIml.findById(id).stream()
                    .filter(x->x.getId().equals(id))
                    .findFirst().orElse(null);
        }

        return userFlightDataIml.findByChatId(chatID)
                .stream().filter(x -> x.getChatId().equals(chatID))
                .findFirst()
                .orElse(null);
    }

    public List<UserSubscription> findSubByChatId (Long chatID) {
        return subscriptionRepository.findByChatId(chatID);
    }

    public void saveSubscription(UserSubscription userSubscription){
        subscriptionRepository.save(userSubscription);
    }

    public void deleteSubscription(String id){
        subscriptionRepository.deleteById(id);
    }
}
