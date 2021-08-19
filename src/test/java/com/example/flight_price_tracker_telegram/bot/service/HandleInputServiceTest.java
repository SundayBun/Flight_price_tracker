package com.example.flight_price_tracker_telegram.bot.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HandleInputServiceTest {

    @Test
    void formatDate() {
        String dateExp = "2021-05-06";
        String dateVer = HandleInputService.formatDate("06.05.2021");
        assertThat(dateVer).isEqualTo(dateExp);
    }
}