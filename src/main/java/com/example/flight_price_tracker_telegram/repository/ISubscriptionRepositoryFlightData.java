package com.example.flight_price_tracker_telegram.repository;

import com.example.flight_price_tracker_telegram.model.browse.BrowsePlacesDTO;
import com.example.flight_price_tracker_telegram.model.browse.FlightPricesDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubscriptionRepositoryFlightData extends MongoRepository<UserFlightData,Long> {

    List<UserFlightData> findByChatId(Long chatID);

    List<UserFlightData> findById(String id);

}
