package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.HandleInputService;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.Locale;

public class DestinationPlaceTextState extends State {

    boolean changeState = false;

    public DestinationPlaceTextState(Context context) {
        super(context);
        this.textMessageRequest = true;
        this.queryResponse = false;
        this.stateName = StateName.DESTINATION_PLACE_TEXT;
        localeMessageService.setLocale(Locale.forLanguageTag(context.getUserData().getLocale()));
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessage.sendMessage(context,localeMessageService.getMessage("state.destinationPlaceText",Emojis.TO,Emojis.MAG_RIGHT),null);
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setStateName(stateName);

        if (HandleInputService.places(context) != null) {
            changeState = true;
        }
    }

    @Override
    public State nextState() {
        if (changeState) {
            context.setState(new DestinationPlaceButtonsState(context));
        } else {
            context.setState(new DestinationPlaceTextState(context));
        }
        return context.getState();
    }
}
