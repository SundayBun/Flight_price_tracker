package com.example.flight_price_tracker_telegram.bot.refactored.service.strategy;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.statesMethodSequence.JustEnterMethod;
import com.example.flight_price_tracker_telegram.bot.refactored.statesMethodSequence.StatesMethodSequence;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

@Slf4j
@EqualsAndHashCode
public class NewUserStepStrategy implements IStepStrategy{

    private Context context;
    private StatesMethodSequence statesMethodSequence;

    public NewUserStepStrategy(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return statesMethodSequence.getContext();
    }

    @Override
    public BotApiMethod<?> doStateLogic() {
        statesMethodSequence=new JustEnterMethod(context);

        log.info("Just enter method (new User)");

        return statesMethodSequence.getStatesMethodSequence();
    }
}
