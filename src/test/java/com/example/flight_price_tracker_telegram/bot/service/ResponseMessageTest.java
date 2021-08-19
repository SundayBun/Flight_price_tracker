package com.example.flight_price_tracker_telegram.bot.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ResponseMessageTest {

    @Test
    void getDate() {
        String date = "2021-05-06";
        String date2 = ResponseMessage.getDate(date, "ru-RU");
        assertThat("06 мая 2021").isEqualTo(date2);
    }
}