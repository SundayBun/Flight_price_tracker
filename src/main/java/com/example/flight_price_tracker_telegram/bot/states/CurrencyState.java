package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.Locale;

public class CurrencyState extends State {

    public CurrencyState(Context context) {
        super(context);
        this.textMessageRequest = false;
        this.queryResponse = true;
        this.stateName = StateName.CURRENCY;
        localeMessageService.setLocale(Locale.forLanguageTag(context.getUserData().getLocale()));
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessage.sendMessage(context, localeMessageService.getMessage("state.currency", Emojis.MONEYBAG), null);
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setStateName(stateName);
        context.getUserData().setCurrency(context.getCallbackQuery().getData());
    }

    @Override
    public State nextState() {
        context.setState(new OriginPlaceText(context));
        return context.getState();
    }
}
