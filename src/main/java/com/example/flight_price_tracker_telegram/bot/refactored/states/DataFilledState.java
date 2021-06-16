package com.example.flight_price_tracker_telegram.bot.refactored.states;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.service.ResponseMessageRef;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import com.example.flight_price_tracker_telegram.model.service.FlightPriceClientImpl;
import com.example.flight_price_tracker_telegram.model.service.FlightPriceDateClientImpl;
import com.example.flight_price_tracker_telegram.model.service.IFlightPriceClient;
import com.example.flight_price_tracker_telegram.model.service.IFlightPriceDateClient;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class DataFilledState extends State{

    public DataFilledState(Context context) {
        super(context);
        this.textMessageRequest=false;
        this.queryResponse=true;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return ResponseMessageRef.sendMessage(context, "Tap on " + Emojis.PLANE + " to find current min price info");
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setState(this);
        if (context.getCallbackQuery().getData().equals("Button \"Find price\" has been pressed")) {
            sendQueryForPrice(context);
            //вызвать страницу скайсканера и сохранить мин цену в UserData
            context.setState(new DataTransferredState(context));
        } else {
            context.setState(new DataFilledState(context));
        }
    }

    @Override
    public State nextState() {
        return context.getState();
    }

    public void sendQueryForPrice(Context context) {

        if (context.getUserFlightData().getInboundPartialDate() != null) {
            IFlightPriceDateClient priceDateClient = new FlightPriceDateClientImpl();
            context.getUserFlightData().setSkyScannerResponseDates(priceDateClient.browseQuotes(context.getUserData()
                    , context.getUserFlightData()));

            context.getUserFlightData().setRequestNotNull(context.getUserFlightData().getSkyScannerResponseDates() != null && context.getUserFlightData().getSkyScannerResponseDates().getQuotes().size()!=0);

            // log.info("Search result: {}", context.getUserFlightData().getSkyScannerResponseDates().toString());

        } else {
            IFlightPriceClient priceClient = new FlightPriceClientImpl();
            context.getUserFlightData().setSkyScannerResponseQuotes(priceClient.browseQuotes(context.getUserData()
                    , context.getUserFlightData()));

            context.getUserFlightData().setRequestNotNull(context.getUserFlightData().getSkyScannerResponseQuotes() != null && context.getUserFlightData().getSkyScannerResponseQuotes().getQuotes().size()!=0);

            //  log.info("Search result: {}", context.getUserFlightData().getSkyScannerResponseQuotes().toString());
        }
    }
}
