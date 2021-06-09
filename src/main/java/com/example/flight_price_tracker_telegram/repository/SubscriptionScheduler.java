package com.example.flight_price_tracker_telegram.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionScheduler {

    @Autowired
    ISubscriptionRepository subscriptionRepository;


    public List<UserSubscription> findAll(){
 return subscriptionRepository.findAll();
    }

    public void save(UserSubscription userSubscription){
        subscriptionRepository.save(userSubscription);
    }

}
