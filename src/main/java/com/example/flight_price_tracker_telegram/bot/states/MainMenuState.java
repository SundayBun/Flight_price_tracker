package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class MainMenuState extends State {


    public MainMenuState(Context context) {
        super(context);
        this.textMessageRequest=true;
        this.queryResponse=false;
        this.stateName = StateName.MAIN_MENU;

    }

    @Override
    public BotApiMethod<?> enter(Context context) {
        return null; //ResponseMessage.sendMessage(context,  "Main menu");
    }

    @Override
    public void handleInput(Context context) {
        context.getUserData().setStateName(stateName);

        if (context.getInput().equals("Change localisation info")) {
            context.setState(new StartState(context));
        } else if (context.getInput().equals("New search")) {
            context.setState(new OriginPlaceText(context));
        } else {
            context.setState(new SubscriptionListState(context));
        }
    }
    @Override
    public State nextState() {
        return null;
    }
}
