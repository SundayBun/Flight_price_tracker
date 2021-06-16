package com.example.flight_price_tracker_telegram.bot.refactored.states;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.service.ResponseMessageRef;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class DataTransferredState extends State{

    public DataTransferredState(Context context) {
        super(context);
        this.textMessageRequest=false;
        this.queryResponse=false;
    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        if(!context.getUserFlightData().isRequestNotNull()){

            return ResponseMessageRef.sendErrorSearchResult(context,"No flights have been found. \n Try to change query. "); //Invalid input data or no s
        }
        return ResponseMessageRef.sendSearchResult(context);
    }

    @Override
    public void handleInput(Context context) {

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
            context.setState(new SubscriptionState(context));
        } else {
            context.setState(new MainMenuState(context));
        }
    }

    @Override
    public State nextState() {
        return context.getState();
    }
}
