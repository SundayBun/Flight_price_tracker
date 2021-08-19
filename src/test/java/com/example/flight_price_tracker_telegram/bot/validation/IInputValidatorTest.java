package com.example.flight_price_tracker_telegram.bot.validation;


import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.LocaleMessageService;
import com.example.flight_price_tracker_telegram.bot.states.*;
import com.example.flight_price_tracker_telegram.prototype.MessagePrototype;
import com.example.flight_price_tracker_telegram.prototype.UpdatePrototype;
import com.example.flight_price_tracker_telegram.prototype.UsersPrototype;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

class IInputValidatorTest {

    Context context;
    Update update;
    IInputValidator inputValidator;
    LocaleMessageService localeMessageService;

    @BeforeEach
    void setUp() {
        localeMessageService= Mockito.mock(LocaleMessageService.class);
        context = new Context();
        context.setState(new StartState(context));
        context.getState().setLocaleMessageService(localeMessageService);
        context.setUserData(UsersPrototype.aUserData());
        context.getUserData().setLocale("en-US");
        inputValidator=new InputValidatorImpl();

    }

    @Test
    void isValidInputUpdateHasMessageCallbackQuery() {
        Mockito.doNothing().when(localeMessageService).setLocale(any());
        update = UpdatePrototype.aUpdateMessageCallbackQuery();
        boolean validResult=inputValidator.isValidInput(update,context);
        assertThat(validResult).isEqualTo(true);
    }

    @Test
    void isValidInputUpdateHasCallbackQuery() {
        Mockito.doNothing().when(localeMessageService).setLocale(any());
        context.setState(new CountryButtonState(context));
        update = UpdatePrototype.aUpdateCallbackQuery();

        boolean validResult=inputValidator.isValidInput(update,context);
        assertThat(validResult).isEqualTo(true);
    }

    @Test
    void isValidInputUpdateHasMessage() {
        context.setState(new StartState(context));
        update = UpdatePrototype.aUpdateMessage();

        boolean validResult=inputValidator.isValidInput(update,context);
        assertThat(validResult).isEqualTo(false);

        context.setState(new InboundPartialDateState(context));
        update = UpdatePrototype.aUpdateCallbackQuery();

        boolean validResult2=inputValidator.isValidInput(update,context);
        assertThat(validResult2).isEqualTo(true);
    }

    @Test
    void isValidInputHasNoMessageNoCallbackQuery() {
        update=new Update();
        update.setMessage(MessagePrototype.aMessage());
        context.getUserData().setLocale("en-US");
        context.setState(new MainMenuState(context));
        boolean validResult2=inputValidator.isValidInput(update,context);
        assertThat(validResult2).isEqualTo(true);
    }
}