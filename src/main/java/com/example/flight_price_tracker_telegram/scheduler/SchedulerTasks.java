package com.example.flight_price_tracker_telegram.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SchedulerTasks {

    private static final long TEN_MINUTES = 1000 * 60 * 10;

    @Scheduled(fixedRate = TEN_MINUTES)
    public void recountMinPrice() {
        log.debug("recount minPrice Started");
        recountMinPriceService.recount();
        log.debug("recount minPrice finished");
    }
}
