package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import com.example.flight_price_tracker_telegram.skyscanner_api.service.FlightPriceClientImpl;
import com.example.flight_price_tracker_telegram.skyscanner_api.service.FlightPriceDateClientImpl;
import com.example.flight_price_tracker_telegram.skyscanner_api.service.IFlightPriceClient;
import com.example.flight_price_tracker_telegram.skyscanner_api.service.IFlightPriceDateClient;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.Locale;

public class DataFilledState extends State {

    boolean changeState = false;

    public DataFilledState(Context context) {
        super(context);
        this.textMessageRequest = false;
        this.queryResponse = true;
        this.stateName = StateName.DATA_FILLED;
        localeMessageService.setLocale(Locale.forLanguageTag(context.getUserData().getLocale()));
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessage.sendMessage(context, localeMessageService.getMessage("state.dataFilled",Emojis.PLANE),null);
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setStateName(stateName);
        if (context.getCallbackQuery().getData().equals("Button \"Find price\" has been pressed")) {
            sendQueryForPrice(context);
            context.getUserData().setId(context.getCallbackQuery().getId());
            changeState = true;
        }
    }

    @Override
    public State nextState() {
        if (changeState) {
            context.setState(new DataTransferredState(context));
        } else {
            context.setState(new DataFilledState(context));
        }
        return context.getState();
    }

    public void sendQueryForPrice(Context context) {

        if (context.getUserFlightData().getInboundPartialDate() != null) {
            IFlightPriceDateClient priceDateClient = new FlightPriceDateClientImpl();
            context.getUserFlightData().setSkyScannerResponseDates(priceDateClient.browseQuotes(context.getUserData()
                    , context.getUserFlightData()));

            context.getUserFlightData().setRequestNotNull(context.getUserFlightData().getSkyScannerResponseDates() != null && context.getUserFlightData().getSkyScannerResponseDates().getQuotes().size() != 0);

            // log.info("Search result: {}", context.getUserFlightData().getSkyScannerResponseDates().toString());

        } else {
            IFlightPriceClient priceClient = new FlightPriceClientImpl();
            context.getUserFlightData().setSkyScannerResponseQuotes(priceClient.browseQuotes(context.getUserData()
                    , context.getUserFlightData()));

            context.getUserFlightData().setRequestNotNull(context.getUserFlightData().getSkyScannerResponseQuotes() != null && context.getUserFlightData().getSkyScannerResponseQuotes().getQuotes().size() != 0);

            //  log.info("Search result: {}", context.getUserFlightData().getSkyScannerResponseQuotes().toString());
        }
    }
}
