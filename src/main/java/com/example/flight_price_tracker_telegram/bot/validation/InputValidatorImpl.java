package com.example.flight_price_tracker_telegram.bot.validation;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class InputValidatorImpl implements IInputValidator{
    @Override
    public boolean isValidInput(Update update, Context context) {
        return (context.getState().isQueryResponse() && update.hasCallbackQuery())
         || (context.getState().isTextMessageRequest() && update.hasMessage());
                //(context.getStateFromUserData().isQueryResponse() && !context.getStateFromUserData().isTextMessageRequest() && update.hasCallbackQuery())
               // || (!context.getStateFromUserData().isQueryResponse() && context.getStateFromUserData().isTextMessageRequest() && update.hasMessage());
    }
}
