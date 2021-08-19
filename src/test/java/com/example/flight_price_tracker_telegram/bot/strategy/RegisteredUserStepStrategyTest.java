package com.example.flight_price_tracker_telegram.bot.strategy;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.states.StateName;
import com.example.flight_price_tracker_telegram.bot.statesMethodSequence.RegularSequence;
import com.example.flight_price_tracker_telegram.bot.statesMethodSequence.StatesMethodSequence;
import com.example.flight_price_tracker_telegram.prototype.UpdatePrototype;
import com.example.flight_price_tracker_telegram.prototype.UsersPrototype;
import com.example.flight_price_tracker_telegram.repository.UserSubscriptionDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import static org.assertj.core.api.Assertions.assertThat;


class RegisteredUserStepStrategyTest {

    Context context;
    Update update;
    RegisteredUserStepStrategy registeredUserStepStrategy;
    UserSubscriptionDataService repository;
    StatesMethodSequence statesMethodSequence;

    @BeforeEach
    void setUp() {
        context = new Context();
        context.setUserData(UsersPrototype.aUserData());
        context.getUserData().setLocale("en-US");

        update = UpdatePrototype.aUpdateCallbackQuery();
        repository = null;
    }

    @Test
    void checkIfButtonCommand() {
        update.getCallbackQuery().setData("EUR");
        statesMethodSequence = new RegularSequence(context);
        registeredUserStepStrategy = new RegisteredUserStepStrategy(context, update, repository);
        registeredUserStepStrategy.setStatesMethodSequence(statesMethodSequence);

        registeredUserStepStrategy.checkIfButtonCommand();
        StateName stateName = registeredUserStepStrategy.getContext().getUserData().getStateName();
        assertThat(stateName).isEqualTo(StateName.CURRENCY);
    }

    @Test
    void getStateFromUserData() {
        context.getUserData().setStateName(StateName.INBOUND_PARTIAL_DATE);
        statesMethodSequence = new RegularSequence(context);
        registeredUserStepStrategy = new RegisteredUserStepStrategy(context, update, repository);
        registeredUserStepStrategy.setStatesMethodSequence(statesMethodSequence);

        registeredUserStepStrategy.getStateFromUserData();
        StateName stateName = registeredUserStepStrategy.getContext().getState().getStateName();
        assertThat(stateName).isEqualTo(StateName.INBOUND_PARTIAL_DATE);
    }
}