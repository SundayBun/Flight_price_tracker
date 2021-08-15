package com.example.flight_price_tracker_telegram.bot.strategy;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.states.StartState;
import com.example.flight_price_tracker_telegram.bot.states.State;
import com.example.flight_price_tracker_telegram.bot.statesMethodSequence.JustEnterMethod;
import com.example.flight_price_tracker_telegram.bot.statesMethodSequence.RegularSequence;
import com.example.flight_price_tracker_telegram.bot.statesMethodSequence.StatesMethodSequence;

import static com.example.flight_price_tracker_telegram.prototype.SendMessagePrototype.aSendMessage;

import com.example.flight_price_tracker_telegram.prototype.UpdatePrototype;
import com.example.flight_price_tracker_telegram.prototype.UsersPrototype;
import com.example.flight_price_tracker_telegram.repository.UserSubscriptionDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class IStepStrategyTest {

    RegisteredUserStepStrategy registeredUserStepStrategy;
    Context context;
    StatesMethodSequence statesMethodSequence;
    State state;


    @BeforeEach
    void setUp() {
        registeredUserStepStrategy=mock(RegisteredUserStepStrategy.class);
        state = mock(State.class);
        context = new Context();
        context.setState(state);
    }

    @Test
    void doStateLogicNewUser() {
        Mockito.doReturn(aSendMessage()).when(state).enter(any());

        BotApiMethod<?> someData = aSendMessage();
        statesMethodSequence = new JustEnterMethod(context);
        BotApiMethod<?> outgoingData = statesMethodSequence.getStatesMethodSequence();
        assertThat(outgoingData).isNotNull();
        assertThat(outgoingData).isEqualTo(someData);
    }

    @Test
    void doStateLogicRegisteredUser() {
        doNothing().when(registeredUserStepStrategy).checkIfButtonCommand();
        doNothing().when(registeredUserStepStrategy).getStateFromUserData();
        Mockito.doReturn(aSendMessage()).when(state).enter(any());
        doNothing().when(state).handleInput(any());
        Mockito.doReturn(null).when(state).nextState();

        BotApiMethod<?> someData = aSendMessage();
        statesMethodSequence = new RegularSequence(context);
        BotApiMethod<?> outgoingData = statesMethodSequence.getStatesMethodSequence();
        assertThat(outgoingData).isNotNull();
        assertThat(outgoingData).isEqualTo(someData);
    }

}