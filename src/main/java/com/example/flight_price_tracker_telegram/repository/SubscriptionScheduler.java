package com.example.flight_price_tracker_telegram.repository;

import com.example.flight_price_tracker_telegram.repository.entity.UserSubscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionScheduler {

    @Autowired
    ISubscriptionRepositoryUserSubscription subscriptionRepository;

    public List<UserSubscription> findAll() {
        return subscriptionRepository.findAll();
    }

    public void save(UserSubscription userSubscription) {
        subscriptionRepository.save(userSubscription);
    }

}
