package com.example.flight_price_tracker_telegram.bot;

import com.example.flight_price_tracker_telegram.bot.states.StartState;
import com.example.flight_price_tracker_telegram.bot.statesMethodSequence.RegularSequence;
import com.example.flight_price_tracker_telegram.bot.statesMethodSequence.StatesMethodSequence;
import com.example.flight_price_tracker_telegram.bot.strategy.RegisteredUserStepStrategy;
import com.example.flight_price_tracker_telegram.prototype.CallbackQueryPrototype;
import com.example.flight_price_tracker_telegram.prototype.UpdatePrototype;
import com.example.flight_price_tracker_telegram.prototype.UsersPrototype;
import com.example.flight_price_tracker_telegram.repository.UserSubscriptionDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class TelegramFacadeTest {
    Context context;
    Update update;
    UserSubscriptionDataService repository;
    TelegramFacade telegramFacade;
    BotApiMethod<?> outgoingData;
    BotApiMethod<?> outgoingDataExpected;
    StatesMethodSequence statesMethodSequence;

    long chatId;


    @BeforeEach
    void setUp() {
        chatId=1L;
        context = new Context();
        context.setUserData(UsersPrototype.aUserData());
        context.setCallbackQuery(CallbackQueryPrototype.aCallbackQuery());
        context.setState(new StartState(context));
        update = UpdatePrototype.aUpdateMessageCallbackQuery();
        repository = mock(UserSubscriptionDataService.class);
    }

    @Test
    void handleUpdate() {
        telegramFacade=new TelegramFacade();
        telegramFacade.setContext(context);
        telegramFacade.setRepository(repository);

        Mockito.doReturn(UsersPrototype.aUserData()).when(repository).findByChatIdOrId(any(),any());
        Mockito.doReturn(UsersPrototype.aUserFlightData()).when(repository).findByIdOrChatID(any(),any());
        Mockito.doNothing().when(repository).saveUserData(any());
        Mockito.doNothing().when(repository).saveUserFlightData(any());

        statesMethodSequence = new RegularSequence(context);
        RegisteredUserStepStrategy registeredUserStepStrategy=new RegisteredUserStepStrategy(context,update,repository);
        registeredUserStepStrategy.setStatesMethodSequence(statesMethodSequence);
        outgoingDataExpected=registeredUserStepStrategy.doStateLogic();

        outgoingData=telegramFacade.handleUpdate(update);
        Assertions.assertThat(outgoingDataExpected).isEqualTo(outgoingData);
    }
}