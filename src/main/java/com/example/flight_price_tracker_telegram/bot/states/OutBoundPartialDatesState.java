package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.HandleInput;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import com.example.flight_price_tracker_telegram.bot.validation.ValidatorDateImpl;
import com.example.flight_price_tracker_telegram.bot.validation.IValidator;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class OutBoundPartialDatesState extends State{

    final IValidator datesValidator = new ValidatorDateImpl(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    boolean changeState=false;

    public OutBoundPartialDatesState(Context context) {
        super(context);
        this.textMessageRequest=true;
        this.queryResponse=false;
        this.stateName = StateName.OUTBOUND_PARTIAL_DATE;
        localeMessageService.setLocale(Locale.forLanguageTag(context.getUserData().getLocale()));
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessage.sendMessage(context, localeMessageService.getMessage("state.outBoundPartialDates",Emojis.DATE),null);
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setStateName(stateName);

        if (datesValidator.isValid(context.getInput())) {
            String validDate= HandleInput.formatDate(context.getInput());
            context.getUserFlightData().setOutboundPartialDate(validDate);
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
