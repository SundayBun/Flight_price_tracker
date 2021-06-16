package com.example.flight_price_tracker_telegram.bot.refactored.states;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.service.ResponseMessageRef;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class CurrencyState extends State{

    public CurrencyState(Context context) {
        super(context);
        this.textMessageRequest=false;
        this.queryResponse=true;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessageRef.sendMessage(context,
                Emojis.MONEYBAG + " Select the currency" + Emojis.MONEYBAG);
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setState(this);
        context.getUserData().setCurrency(context.getCallbackQuery().getData());
    }

    @Override
    public State nextState() {
        context.setState(new OriginPlaceText(context));
        return context.getState();
    }
}
