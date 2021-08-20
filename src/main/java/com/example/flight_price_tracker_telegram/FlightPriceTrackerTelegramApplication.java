package com.example.flight_price_tracker_telegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableScheduling
public class FlightPriceTrackerTelegramApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightPriceTrackerTelegramApplication.class, args);
    }

}
