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

    public InboundPartialDateState(Context context) {
        super(context);
        this.textMessageRequest=true;
        this.queryResponse=true;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessageRef.sendMessage(context,  Emojis.DATE + " Enter the inbound partial date (yyyy-mm-dd) or tap on \"One way\"");
    }

    @Override
    public void handleInput(Context context) {

        if (context.getCallbackQuery() != null && context.getCallbackQuery().getData().equals("Button \"One way\" has been pressed")) {
            context.getUserFlightData().setInboundPartialDate(null);
            context.setState(new DataFilledState(context));
        } else {
            context.getUserData().setState(this);

            if(datesValidator.isValid(context.getInput())){
                context.getUserFlightData().setInboundPartialDate(context.getInput());
                context.setState(new DataFilledState(context));
            } else context.setState(new InboundPartialDateState(context));
        }
    }

    @Override
    public State nextState() {
        return context.getState();
    }
}
