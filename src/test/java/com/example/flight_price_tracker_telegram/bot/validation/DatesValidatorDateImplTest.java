package com.example.flight_price_tracker_telegram.bot.validation;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class DatesValidatorDateImplTest {

    ValidatorDateImpl datesValidator;

    @Test
    void isValid() {
        datesValidator = new ValidatorDateImpl(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        boolean isValid1 = datesValidator.isValid("447444");
        assertThat(isValid1).isFalse();

        boolean isValid2 = datesValidator.isValid("15.02.2021");
        assertThat(isValid2).isTrue();
    }
}