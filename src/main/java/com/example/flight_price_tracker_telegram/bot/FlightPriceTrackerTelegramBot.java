package com.example.flight_price_tracker_telegram.bot;


import com.example.flight_price_tracker_telegram.bot.refactored.TelegramFacadeRef;
import com.example.flight_price_tracker_telegram.bot.refactored.TelegramFacadeRef2;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;


@Getter
@Setter
@PropertySource("classpath:application.properties")
@Component
public class FlightPriceTrackerTelegramBot  extends TelegramWebhookBot {

    @Value("${telegrambot.webHookPath}")
    private String webHookPath;
    @Value("${telegrambot.botuserName}")
    private String botUserName;
    @Value("${telegrambot.botToken}")
    private String botToken;

//    @Autowired
//    private TelegramFacade telegramFacade;

    @Autowired
    private TelegramFacadeRef2 telegramFacadeRef2;

    @Autowired
    private TelegramFacadeRef telegramFacadeRef;

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {

        if (update.getMessage() != null || update.hasCallbackQuery()) {
           return telegramFacadeRef2.handleUpdate(update);
        }
        return null;
    }

}
