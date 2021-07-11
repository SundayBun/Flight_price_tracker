package com.example.flight_price_tracker_telegram.bot.refactored.states;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.service.ResponseMessageRef;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import com.example.flight_price_tracker_telegram.model.validations.DatesValidatorImpl;
import com.example.flight_price_tracker_telegram.model.validations.IDatesValidator;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.time.format.DateTimeFormatter;

public class InboundPartialDateState extends State{

    final IDatesValidator datesValidator = new DatesValidatorImpl(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    boolean dataFilled=false;

    public InboundPartialDateState(Context context) {
        super(context);
        this.textMessageRequest=true;
        this.queryResponse=true;
        this.stateName = StateName.INBOUND_PARTIAL_DATE;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessageRef.sendMessage(context,  Emojis.DATE + " Enter the inbound partial date (yyyy-mm-dd) or tap on \"One way\"");
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setStateName(stateName);
        if (context.getCallbackQuery() != null && context.getCallbackQuery().getData().equals("Button \"One way\" has been pressed")) {
            context.getUserFlightData().setInboundPartialDate(null);
            dataFilled=true;
        } else {
            if(datesValidator.isValid(context.getInput())){
                context.getUserFlightData().setInboundPartialDate(context.getInput());
                dataFilled=true;
            }
        }
    }

    @Override
    public State nextState() {
        if (dataFilled) {
            context.setState(new DataFilledState(context));
        } else {
            context.setState(new InboundPartialDateState(context));
        }
        return context.getState();
    }
}
