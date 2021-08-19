package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.HandleInputService;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import com.example.flight_price_tracker_telegram.bot.validation.ValidatorDateImpl;
import com.example.flight_price_tracker_telegram.bot.validation.IValidator;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class InboundPartialDateState extends State {

    final IValidator datesValidator = new ValidatorDateImpl(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    boolean dataFilled = false;

    public InboundPartialDateState(Context context) {
        super(context);
        this.textMessageRequest = true;
        this.queryResponse = true;
        this.stateName = StateName.INBOUND_PARTIAL_DATE;
        localeMessageService.setLocale(Locale.forLanguageTag(context.getUserData().getLocale()));
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessage.sendMessage(context, localeMessageService.getMessage("state.inboundPartialDates", Emojis.DATE), localeMessageService.getMessage("state.inboundPartialDatesButton"));
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setStateName(stateName);
        if (context.getCallbackQuery() != null && context.getCallbackQuery().getData().equals("Button \"One way\" has been pressed")) {
            context.getUserFlightData().setInboundPartialDate(null);
            dataFilled = true;
        } else {
            if (datesValidator.isValid(context.getInput())) {
                String validDate = HandleInputService.formatDate(context.getInput());
                context.getUserFlightData().setInboundPartialDate(validDate);
                dataFilled = true;
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
