package com.example.flight_price_tracker_telegram.bot;

import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import com.example.flight_price_tracker_telegram.model.browse.FlightPricesDTO;
import com.example.flight_price_tracker_telegram.model.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.model.service.FlightPriceClientImpl;
import com.example.flight_price_tracker_telegram.model.service.IFlightPriceClient;
import com.example.flight_price_tracker_telegram.model.service.ILocalisationClient;
import com.example.flight_price_tracker_telegram.model.service.LocalisationClientImpl;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.Locale;

/**
 * Возможные состояния бота
 */

@Slf4j
public enum BotState {
    START(true, true) {
        @Override
        public SendMessage enter(BotStateContextRepo context) {
            log.info("!!! MESSAGE: state:{}, message: {}", this, context.getUserData());

            return ResponseMessage.sendMessage(context, this, isQueryResponse(), "Choose the language");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {
            if (context.getCallbackQuery().getData().equals("Button \"ENG\" has been pressed")) {
                context.getUserData().setLocale("en-US");
            } else {
                context.getUserData().setLocale("ru-RU");
            }
        }

        @Override
        public BotState nextState() {
            return COUNTRY_TEXT;
        }
    },
    COUNTRY_TEXT(true, false) {
        @Override
        public SendMessage enter(BotStateContextRepo context) {

            return ResponseMessage.sendMessage(context, this, isQueryResponse(), "Enter the country. " +
                            "\n (enter at least one letter and send it to see available countries");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {
            context.getUserData().setStateID(this.ordinal());


            context.getUserData().setCountry(context.getInput());
        }

        @Override
        public BotState nextState() {
            return COUNTRY_BUTTONS;
        }
    },
    COUNTRY_BUTTONS(true, true) {
        @Override
        public SendMessage enter(BotStateContextRepo context) {

            return ResponseMessage.sendMessage(context, this, isQueryResponse()
                    , "Select the country you are in)");

        }

    },

    CURRENCY(true, false) {
        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendMessage(context, this, isQueryResponse(), "Enter the currency");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {
            context.getUserData().setStateID(this.ordinal());
            context.getUserData().setCurrency(context.getInput());
        }

        @Override
        public BotState nextState() {
            return LOCALE;
        }
    },
    LOCALE(true, false) {
        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendMessage(context, this, isQueryResponse(), "Enter the locale");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {
            context.getUserData().setStateID(this.ordinal());
            context.getUserData().setLocale(context.getInput());
        }

        @Override
        public BotState nextState() {
            return ORIGIN_PLACE;
        }
    },
    ORIGIN_PLACE(true, false) {
        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendMessage(context, this, isQueryResponse(), "Enter the origin place");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {
            context.getUserFlightData().setChatId(context.getUserData().getChatId());
            context.getUserData().setStateID(this.ordinal());
            context.getUserFlightData().setOriginPlace(context.getInput());
        }

        @Override
        public BotState nextState() {
            return DESTINATION_PLACE;
        }
    },
    DESTINATION_PLACE(true, false) {
        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendMessage(context, this, isQueryResponse(), "Enter the destination place");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {
            context.getUserData().setStateID(this.ordinal());
            context.getUserFlightData().setDestinationPlace(context.getInput());
        }

        @Override
        public BotState nextState() {
            return OUTBOUND_PARTIAL_DATE;
        }
    },
    OUTBOUND_PARTIAL_DATE(true, false) {
        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendMessage(context, this, isQueryResponse(), "Enter the outbound partial date");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {
            context.getUserData().setStateID(this.ordinal());
            context.getUserFlightData().setOutboundPartialDate(context.getInput());
        }

        @Override
        public BotState nextState() {
            return INBOUND_PARTIAL_DATE;
        }
    },
    INBOUND_PARTIAL_DATE(true, false) {
        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendMessage(context, this, isQueryResponse(), "Enter the inbound partial date");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {
            context.getUserData().setStateID(this.ordinal());
            context.getUserFlightData().setInboundPartialDate(context.getInput());
        }

        @Override
        public BotState nextState() {
            return DATA_FILLED;
        }
    },
    DATA_FILLED(true, true) {
        private BotState next;

        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendMessage(context, this, isQueryResponse(), "Tap on \"Find price\" to find current min price info");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {
            context.getUserData().setStateID(this.ordinal());
            if (context.getCallbackQuery().getData().equals("Button \"Find price\" has been pressed")) {
                sendQueryForPrice(context);
                //вызвать страницу скайсканера и сохранить мин цену в UserData
                next = DATA_TRANSFERRED;
            } else {
                next = ASK_TO_TAP;
            }

        }

        @Override
        public BotState nextState() {
            return next;
        }
    },
    DATA_TRANSFERRED(false, false) {
        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendSearchResult(context);
        }

        @Override
        public BotState nextState() {
            return ASK_TO_TRACK;
        }
    },

    ASK_TO_TRACK(true, true) {
        @Override
        public SendMessage enter(BotStateContextRepo context, FlightPricesDTO flightPricesDTO) {
            return null;
        }

        @Override
        public BotState nextState() {
            return null;
        }
    },

    ASK_TO_TAP(false, false) {
        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendMessage(context, this, isQueryResponse(), "Tap on the button below");
        }

        @Override
        public BotState nextState() {
            return DATA_FILLED; // поменять на вызов по context.getStateID
        }
    };

    private static BotState[] states;
    private final boolean inputNeeded;
    private final boolean queryResponse;
    // private FlightPricesDTO pricesDTO;

    BotState() {
        this.inputNeeded = true; // по умолчанию- ждем действия
        this.queryResponse = true; //ждем ответа от кнопок
    }

    BotState(boolean inputNeeded, boolean queryResponse) {
        this.inputNeeded = inputNeeded; //или задаем самостоятельно
        this.queryResponse = queryResponse;
    }

    // возвращает первоначальное состояние,
    // в которое надо перейти при регистрации нового пользователя
    public static BotState getInitialState() {
        return byId(0);
    }

    public static BotState byId(int id) {

        if (states == null) { //если states еще не инициализирован
            states = BotState.values(); //массиву states передаем все значения enum-а
        }
        return states[id]; //возвращаем значение по индексу
    }

    public boolean isInputNeeded() {
        return inputNeeded;
    }

    public boolean isQueryResponse() {
        return queryResponse;
    }

//    public FlightPricesDTO pricesDTO() {
//        return pricesDTO;
//    }

    // обрабатывает ввод пользователя в текущем состоянии
    //когда пользователь что-то посылает срабатывает метод handleInput
    // do nothing by default
    public void handleInput(BotStateContextRepo context) {
    }

    public SendMessage enter(BotStateContextRepo context) {
        return null;
    } //войти в состояние

    public SendMessage enter(BotStateContextRepo context, FlightPricesDTO flightPricesDTO) {
        return null;
    }

    public void sendQueryForPrice(BotStateContextRepo context) {
        IFlightPriceClient priceClient = new FlightPriceClientImpl();

        context.getUserFlightData().setSkyScannerResponse(priceClient.browseQuotes(context.getUserData()
                , context.getUserFlightData()));
    }

    public void sendQueryForCountry(BotStateContextRepo context) {
        ILocalisationClient localisationClient = new LocalisationClientImpl();

        List<CountryDTO> countriesList = localisationClient.retrieveCountries(context.getUserData().getLocale());

        if (countriesList != null) {
            countriesList.stream().filter(x -> x.getName().toLowerCase().startsWith(context.getInput().toLowerCase()))
            .findFirst().orElse(null);
        }

    }


    public abstract BotState nextState(); //говорит в какое состояние переходить, когда текущее уже обработанно

}
