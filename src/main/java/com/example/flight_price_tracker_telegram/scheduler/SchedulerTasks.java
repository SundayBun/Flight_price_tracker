package com.example.flight_price_tracker_telegram.scheduler;

import com.example.flight_price_tracker_telegram.model.browse.BrowseDatesDTO;
import com.example.flight_price_tracker_telegram.model.service.FlightPriceClientImpl;
import com.example.flight_price_tracker_telegram.model.service.FlightPriceDateClientImpl;
import com.example.flight_price_tracker_telegram.model.service.IFlightPriceClient;
import com.example.flight_price_tracker_telegram.model.service.IFlightPriceDateClient;
import com.example.flight_price_tracker_telegram.repository.SubscriptionScheduler;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SchedulerTasks {

    @Autowired
    SubscriptionScheduler repository;

    private static final long TEN_MINUTES = 1000 * 60 * 10;

    @Scheduled (fixedRate = TEN_MINUTES)
    public void renewSubscript() {
        log.debug("recount minPrice Started");

        IFlightPriceDateClient priceDateClient = new FlightPriceDateClientImpl();
        IFlightPriceClient priceClient = new FlightPriceClientImpl();

        List<UserSubscription> userSubscriptionList = repository.findAll();
        userSubscriptionList.forEach(x ->{
                if(x.getUserFlightData().getInboundPartialDate()!=null){
                x.setSkyScannerResponseDates(priceDateClient.browseQuotes(x.getUserData(), x.getUserFlightData()));
                }else {
                    x.setSkyScannerResponseQuotes(priceClient.browseQuotes(x.getUserData(),x.getUserFlightData()));
                }
        });

        log.debug("recount minPrice finished");
    }
//        for(UserSubscription subscription: userSubscriptionList) {
//            if (subscription.getMinPrice()<subscription.getUserFlightData().getSkyScannerResponseDates().getQuotes().get(0).getMinPrice()){
//
//            }
//        }
//    }
}
