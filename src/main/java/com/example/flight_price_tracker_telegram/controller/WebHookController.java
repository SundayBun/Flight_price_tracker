package com.example.flight_price_tracker_telegram.controller;

import com.example.flight_price_tracker_telegram.bot.FlightPriceTrackerTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 *класс, обрабатывающий get и post запросы
 */

@RestController //@ResponseBody ()+@Controller
public class WebHookController {

    @Autowired
    private final FlightPriceTrackerTelegramBot bot;

    public WebHookController(FlightPriceTrackerTelegramBot bot) {
        this.bot = bot;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BotApiMethod<?> updatesReceived(@RequestBody Update update) { //@RequestBody - получает все тело запроса (в отличии от @RequestParam, который запрашивает парам отдельно)
        return   bot.onWebhookUpdateReceived(update);
    }
}
