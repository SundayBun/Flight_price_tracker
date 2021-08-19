package com.example.flight_price_tracker_telegram.bot.validation;

public class ValidatorPlacesImpl implements IValidator {

    @Override
    public boolean isValid(String data) {
        return data.matches("\\w+");
    }
}
