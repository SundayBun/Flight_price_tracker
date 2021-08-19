package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.HandleInputService;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.Locale;

public class OriginPlaceText extends State {

    boolean changeState = false;

    public OriginPlaceText(Context context) {
        super(context);
        this.textMessageRequest = true;
        this.queryResponse = false;
        this.stateName = StateName.ORIGIN_PLACE_TEXT;
        localeMessageService.setLocale(Locale.forLanguageTag(context.getUserData().getLocale()));
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessage.sendMessageOriginPlace(context, localeMessageService.getMessage("state.originPlaceText",Emojis.FROM,Emojis.MAG_RIGHT),
                localeMessageService.getMessage("state.mainMenuLocalisation"),localeMessageService.getMessage("state.mainMenuNewSearch"),localeMessageService.getMessage("state.mainMenuTrackList"));
    }

    @Override
    public void handleInput(Context context) {
        context.getUserFlightData().setChatId(context.getUserData().getChatId());
        context.getUserData().setStateName(stateName);

        if (HandleInputService.places(context) != null) {
            changeState = true;
        }
    }

    @Override
    public State nextState() {
        if (changeState) {
            context.setState(new OriginPlaceButtons(context));
        } else {
            context.setState(new OriginPlaceText(context));
        }
        return context.getState();
    }
}
