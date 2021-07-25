package com.example.flight_price_tracker_telegram.bot.refactored.service.buttonsCallback;

import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionListStateButtons extends KeyboardMarkups{
    @Override
    public void createKeyboardMarkup() {
        buttonList = new ArrayList<>();
        int n=1;

        for (UserSubscription subscription:subscriptionList){

            InlineKeyboardButton inlineKeyboardButton=new InlineKeyboardButton();
            inlineKeyboardButton.setText(""+n);
            // inlineKeyboardButton.setCallbackData(subscription.getId());
            inlineKeyboardButton.setCallbackData(""+subscriptionList.indexOf(subscription));
            n++;
            buttonList.add(inlineKeyboardButton);

        }

        keyboardButtonsRows.add(buttonList);
    }
}
