package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.LocaleMessageService;
import com.example.flight_price_tracker_telegram.repository.entity.UserSubscription;
import com.example.flight_price_tracker_telegram.repository.UserSubscriptionDataService;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.List;

@EqualsAndHashCode
public abstract class State {

    Context context;
    boolean textMessageRequest;
    boolean queryResponse;
    StateName stateName;
    LocaleMessageService localeMessageService;

    public State(Context context) {
        this.context = context;

        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");

        this.localeMessageService=new LocaleMessageService("en-US",messageSource);
    }

    public boolean isQueryResponse() {
        return queryResponse;
    }
    public boolean isTextMessageRequest() {
        return textMessageRequest;
    }

    public BotApiMethod<?> enter(Context context){
        return null;
    }

    public BotApiMethod<?> enter(Context context,  List<UserSubscription> userSubscriptionList){
        return null;
    }

    public void handleInput(Context context){

    }

    public void handleInput(Context context, UserSubscriptionDataService repository) throws NullPointerException {

    }

    public abstract State nextState();

    public StateName getStateName(){
        return stateName;
    }

    public void setStateName(StateName stateName){
        this.stateName=stateName;
    }

}
