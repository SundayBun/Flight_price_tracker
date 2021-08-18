package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.Locale;

public class DataTransferredState extends State {

    boolean changeState = false;

    public DataTransferredState(Context context) {
        super(context);
        this.textMessageRequest = false;
        this.queryResponse = true;
        this.stateName = StateName.DATA_TRANSFERRED;
        localeMessageService.setLocale(Locale.forLanguageTag(context.getUserData().getLocale()));
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        if (!context.getUserFlightData().isRequestNotNull()) {
            return ResponseMessage.sendErrorSearchResult(context, localeMessageService.getMessage("state.dataTransferredError"));
        }

        if (context.getUserFlightData().getInboundPartialDate() == null) {
            return ResponseMessage.sendSearchResult(context, localeMessageService.getMessage("state.dataTransferredOneWay",
                    ResponseMessage.getPlaceNameFromDTO(context.getUserFlightData().getSkyScannerResponseQuotes().getPlaces(), context.getUserFlightData().getOriginPlace()),
                    ResponseMessage.getPlaceNameFromDTO(context.getUserFlightData().getSkyScannerResponseQuotes().getPlaces(),context.getUserFlightData().getDestinationPlace()),
                    ResponseMessage.getDate(context.getUserFlightData().getOutboundPartialDate(),context.getUserData().getLocale()),
                    context.getUserFlightData().getSkyScannerResponseQuotes().getQuotes().get(0).getMinPrice(),
                    context.getUserFlightData().getSkyScannerResponseQuotes().getCurrencies().get(0).getSymbol(),
                    context.getUserFlightData().getSkyScannerResponseQuotes().getCarriers()),
                    localeMessageService.getMessage("state.dataTransferredButton"));
        } else {
            return ResponseMessage.sendSearchResult(context, localeMessageService.getMessage("state.dataTransferredTwoWays",
                    ResponseMessage.getPlaceNameFromDTO(context.getUserFlightData().getSkyScannerResponseDates().getPlaces(), context.getUserFlightData().getOriginPlace()),
                    ResponseMessage.getPlaceNameFromDTO(context.getUserFlightData().getSkyScannerResponseDates().getPlaces(),context.getUserFlightData().getDestinationPlace()),
                    ResponseMessage.getDate(context.getUserFlightData().getOutboundPartialDate(),context.getUserData().getLocale()),
                    ResponseMessage.getDate(context.getUserFlightData().getInboundPartialDate(),context.getUserData().getLocale()),
                    context.getUserFlightData().getSkyScannerResponseDates().getQuotes().get(0).getMinPrice(),
                    context.getUserFlightData().getSkyScannerResponseDates().getCurrencies().get(0).getSymbol(),
                    context.getUserFlightData().getSkyScannerResponseDates().getCarriers()),
                    localeMessageService.getMessage("state.dataTransferredButton"));
        }

    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setStateName(stateName);
        if (context.getCallbackQuery().getData().equals("Button \"Track it\" has been pressed")) {
            context.getUserSubscription().setChatId(context.getUserData().getChatId());
            context.getUserSubscription().setUserData(context.getUserData());
            context.getUserSubscription().setUserFlightData(context.getUserFlightData());
            if (context.getUserFlightData().getInboundPartialDate() != null) {
                context.getUserSubscription().setSkyScannerResponseDates(context.getUserFlightData().getSkyScannerResponseDates());
                context.getUserSubscription().setMinPrice(context.getUserFlightData().getSkyScannerResponseDates().getQuotes().get(0).getMinPrice());
            } else {
                context.getUserSubscription().setSkyScannerResponseQuotes(context.getUserFlightData().getSkyScannerResponseQuotes());
                context.getUserSubscription().setMinPrice(context.getUserFlightData().getSkyScannerResponseQuotes().getQuotes().get(0).getMinPrice());
            }
            changeState = true;
        }
    }

    @Override
    public State nextState() {
        if (changeState) {
            context.setState(new SubscriptionState(context));
        } else {
            context.setState(new MainMenuState(context));
        }
        return context.getState();
    }
}
