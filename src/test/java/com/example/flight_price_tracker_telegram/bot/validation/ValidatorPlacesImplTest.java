package com.example.flight_price_tracker_telegram.bot.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorPlacesImplTest {
    ValidatorPlacesImpl validatorPlacesImpl = new ValidatorPlacesImpl();

    @Test
    void isValid() {
        String testText1 = "hghgh";
        String testText2 = "testText23";
        String testText3 = "";
        boolean result = validatorPlacesImpl.isValid(testText1);
        assertThat(result).isTrue();
    }
}