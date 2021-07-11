package com.example.flight_price_tracker_telegram.bot.refactored.states;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.service.ResponseMessageRef;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import com.example.flight_price_tracker_telegram.model.validations.DatesValidatorImpl;
import com.example.flight_price_tracker_telegram.model.validations.IDatesValidator;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.time.format.DateTimeFormatter;

public class OutBoundPartialDatesState extends State{

    final IDatesValidator datesValidator = new DatesValidatorImpl(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    boolean changeState=false;

    public OutBoundPartialDatesState(Context context) {
        super(context);
        this.textMessageRequest=true;
        this.queryResponse=false;
        this.stateName = StateName.OUTBOUND_PARTIAL_DATE;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessageRef.sendMessage(context,  Emojis.DATE + " Enter the outbound partial date (yyyy-mm-dd)");
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setStateName(stateName);

        if (datesValidator.isValid(context.getInput())) {
            context.getUserFlightData().setOutboundPartialDate(context.getInput());
            changeState = true;
        }
    }

    @Override
    public State nextState() {
        if (changeState) {
            context.setState(new InboundPartialDateState(context));
        } else {
            context.setState(new OutBoundPartialDatesState(context));
        }
        return context.getState();
    }
}
