package com.example.flight_price_tracker_telegram.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubscriptionRepository extends MongoRepository<UserSubscription,Long> {

    List<UserSubscription> findByChatId (Long chatID);

    void deleteById(String id);

  //  List<UserSubscription> findAll();

}
