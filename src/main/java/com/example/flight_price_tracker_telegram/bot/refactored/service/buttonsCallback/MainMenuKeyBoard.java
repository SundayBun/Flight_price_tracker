package com.example.flight_price_tracker_telegram.bot.refactored.service.buttonsCallback;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class MainMenuKeyBoard extends KeyboardMarkups{
    @Override
    public void createKeyboardMarkup() {
        keyboard= new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        row1.add(new KeyboardButton("Change localisation info"));
        row2.add(new KeyboardButton("New search"));
        row3.add(new KeyboardButton("See your track list"));
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
    }
}
