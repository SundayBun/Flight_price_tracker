package com.example.flight_price_tracker_telegram.bot.validation;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.states.*;
import com.example.flight_price_tracker_telegram.prototype.UpdatePrototype;
import com.example.flight_price_tracker_telegram.prototype.UsersPrototype;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IInputValidatorTest {

//    Context context;
//    Update update;
//    IInputValidator inputValidator;
//
//    @BeforeEach
//    void setUp() {
//        context = new Context();
//        context.setUserData(UsersPrototype.aUserData());
//
//        inputValidator=new InputValidatorImpl();
//    }
//
//    @Test
//    void isValidInputUpdateHasMessageCallbackQuery() {
//        context.setState(new CountryTextState(context));
//        update = UpdatePrototype.aUpdateMessageCallbackQuery();
//
//        boolean validResult=inputValidator.isValidInput(update,context);
//        assertThat(validResult).isEqualTo(true);
//    }
//
//    @Test
//    void isValidInputUpdateHasCallbackQuery() {
//        context.setState(new CountryButtonState(context));
//        update = UpdatePrototype.aUpdateCallbackQuery();
//
//        boolean validResult=inputValidator.isValidInput(update,context);
//        assertThat(validResult).isEqualTo(true);
//    }
//
//    @Test
//    void isValidInputUpdateHasMessage() {
//        context.setState(new StartState(context));
//        update = UpdatePrototype.aUpdateMessage();
//
//        boolean validResult=inputValidator.isValidInput(update,context);
//        assertThat(validResult).isEqualTo(false);
//
//        context.setState(new InboundPartialDateState(context));
//        update = UpdatePrototype.aUpdateCallbackQuery();
//
//        boolean validResult2=inputValidator.isValidInput(update,context);
//        assertThat(validResult2).isEqualTo(true);
//    }
}