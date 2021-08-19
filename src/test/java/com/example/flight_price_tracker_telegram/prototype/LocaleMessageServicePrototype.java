package com.example.flight_price_tracker_telegram.prototype;

import com.example.flight_price_tracker_telegram.bot.service.LocaleMessageService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;


public class LocaleMessageServicePrototype {

    public static LocaleMessageService aLocaleMessageService() {
        LocaleMessageService lms = new LocaleMessageService("en-US", getMessageSource());
        return lms;
    }

    private static ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
