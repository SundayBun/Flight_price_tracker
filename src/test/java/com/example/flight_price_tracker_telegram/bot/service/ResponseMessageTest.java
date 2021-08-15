package com.example.flight_price_tracker_telegram.bot.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ResponseMessageTest {

    @Test
    void getDate() {
        String date="2021-05-06";
        String date2=ResponseMessage.getDate(date);
        assertThat("06 May 2021").isEqualTo(date2);
    }
}