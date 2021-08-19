package com.example.flight_price_tracker_telegram.repository;


import com.example.flight_price_tracker_telegram.repository.entity.UserData;
import com.example.flight_price_tracker_telegram.repository.entity.UserFlightData;
import com.example.flight_price_tracker_telegram.repository.entity.UserSubscription;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSubscriptionDataService {

    ISubscriptionRepositoryUserData userDataRepository;
    ISubscriptionRepositoryFlightData userFlightDataIml;
    ISubscriptionRepositoryUserSubscription subscriptionRepository;

    public UserSubscriptionDataService(ISubscriptionRepositoryUserData userDataRepository, ISubscriptionRepositoryFlightData userFlightData, ISubscriptionRepositoryUserSubscription subscriptionRepository) {
        this.userDataRepository = userDataRepository;
        this.userFlightDataIml = userFlightData;
        this.subscriptionRepository = subscriptionRepository;
    }

    public UserData findByChatIdOrId(String id, Long chatID) {
        if (userDataRepository.findByChatId(chatID) == null) {
            return userDataRepository.findById(id);
        }
        return userDataRepository.findByChatId(chatID);

    }

    public void saveUserData(UserData userData) {
        userDataRepository.save(userData);
    }

    public void saveUserFlightData(UserFlightData userFlightData) {
        userFlightDataIml.save(userFlightData);
    }

    public UserFlightData findByIdOrChatID(String id, Long chatID) {
        if (userFlightDataIml.findByChatId(chatID) == null) {
            return userFlightDataIml.findById(id).stream()
                    .filter(x -> x.getId().equals(id))
                    .findFirst().orElse(null);
        }

        return userFlightDataIml.findByChatId(chatID)
                .stream().filter(x -> x.getChatId().equals(chatID))
                .findFirst()
                .orElse(null);
    }

    public List<UserSubscription> findSubByChatId(Long chatID) {
        return subscriptionRepository.findByChatId(chatID);
    }

    public void saveSubscription(UserSubscription userSubscription) {
        subscriptionRepository.save(userSubscription);
    }

    public void deleteSubscription(String id) {
        subscriptionRepository.deleteById(id);
    }
}
