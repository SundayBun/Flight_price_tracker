package com.example.flight_price_tracker_telegram.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubscriptionRepository {

    List<UserSubscription> findByChatId (Long chatID);

    void save(UserSubscription userSubscription);

    void deleteById(Long id);

}
