package com.example.flight_price_tracker_telegram.bot.validation;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DatesValidatorImplTest {

    DatesValidatorImpl datesValidator;

    @Test
    void isValid() {
        datesValidator=new DatesValidatorImpl(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        boolean isValid1=datesValidator.isValid("447444");
        assertThat(isValid1).isFalse();

        boolean isValid2=datesValidator.isValid("2021-02-15");
        assertThat(isValid2).isTrue();
    }
}