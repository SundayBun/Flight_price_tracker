package com.example.flight_price_tracker_telegram.repository;

import com.example.flight_price_tracker_telegram.repository.entity.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubscriptionRepositoryUserData extends MongoRepository<UserData,Long> {

    UserData findByChatId(Long chatID);
    UserData findById (String id);

}
