package com.example.flight_price_tracker_telegram.repository;

import com.example.flight_price_tracker_telegram.model.browse.BrowsePlacesDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubscriptionRepositoryUserData extends MongoRepository<UserData,Long> {

    UserData findByChatId(Long chatID);
    UserData findById (String id);

    void deleteByChatId(Long chatId);

}
