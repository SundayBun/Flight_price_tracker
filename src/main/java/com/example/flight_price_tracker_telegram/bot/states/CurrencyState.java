package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class CurrencyState extends State{

    public CurrencyState(Context context) {
        super(context);
        this.textMessageRequest=false;
        this.queryResponse=true;
        this.stateName = StateName.CURRENCY;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessage.sendMessage(context,
                Emojis.MONEYBAG + " Select the currency" + Emojis.MONEYBAG);
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
