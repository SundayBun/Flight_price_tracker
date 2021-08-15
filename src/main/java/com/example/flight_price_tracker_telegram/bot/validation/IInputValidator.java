package com.example.flight_price_tracker_telegram.bot.validation;

import com.example.flight_price_tracker_telegram.bot.Context;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface IInputValidator {

    boolean isValidInput(Update update, Context context);
}
