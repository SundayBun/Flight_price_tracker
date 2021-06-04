package com.example.flight_price_tracker_telegram.bot;

import com.example.flight_price_tracker_telegram.bot.service.HandleInput;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import com.example.flight_price_tracker_telegram.bot.service.Subscription;
import com.example.flight_price_tracker_telegram.bot.utils.Emojis;
import com.example.flight_price_tracker_telegram.model.browse.FlightPricesDTO;
import com.example.flight_price_tracker_telegram.model.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.model.service.*;
import com.example.flight_price_tracker_telegram.model.validations.DatesValidatorImpl;
import com.example.flight_price_tracker_telegram.model.validations.IDatesValidator;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;
import com.example.flight_price_tracker_telegram.repository.UserSubscriptionDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Possible bot states
 */

@Slf4j
@Component
public enum BotState {
    START(true, true) {

        @Override
        public SendMessage enter(BotStateContextRepo context) {

          //  log.info("!!! MESSAGE: state:{}, message: {}", this, context.getInput());

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

        BotState next = null;

        @Override
        public SendMessage enter(BotStateContextRepo context) {

            return ResponseMessage.sendMessage(context, this, isQueryResponse(), Emojis.EARTH + " Enter the country. " + Emojis.EARTH +
                    "\n (enter at least one letter and send it to see available countries)");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {
            context.getUserData().setStateID(this.ordinal());

            if (HandleInput.country(context) != null) {
                next = COUNTRY_BUTTONS;
            } else {
                next = COUNTRY_TEXT;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },
    COUNTRY_BUTTONS(true, true) {

        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendSearchCountry(context, HandleInput.country(context)
                    , "Select the country you are in");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {
            context.getUserData().setStateID(this.ordinal());
            context.getUserData().setCountry(context.getCallbackQuery().getData());
        }

        @Override
        public BotState nextState() {
            return CURRENCY;
        }

    },

    CURRENCY(true, true) {
        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendMessage(context, this, isQueryResponse(),
                    Emojis.MONEYBAG + " Select the currency" + Emojis.MONEYBAG);
        }

        @Override
        public void handleInput(BotStateContextRepo context) {
            context.getUserData().setStateID(this.ordinal());
            context.getUserData().setCurrency(context.getCallbackQuery().getData());
        }

        @Override
        public BotState nextState() {
            return ORIGIN_PLACE_TEXT;
        }
    },

    ORIGIN_PLACE_TEXT(true, false) {

        BotState next = null;

        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendMessage(context, this, isQueryResponse(), "Enter the origin place " + Emojis.MAG_RIGHT +
                    "\n (enter at least one letter and send it to see available places)");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {

            context.getUserFlightData().setChatId(context.getUserData().getChatId());
            context.getUserData().setStateID(this.ordinal());

            if (HandleInput.places(context) != null) {
                next = ORIGIN_PLACE_BUTTONS;
            } else {
                next = ORIGIN_PLACE_TEXT;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },
    ORIGIN_PLACE_BUTTONS(true, true) {
        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendSearchPlaces(context, HandleInput.places(context)
                    , "Select the departure place");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {
            context.getUserData().setStateID(this.ordinal());
            context.getUserFlightData().setOriginPlace(context.getCallbackQuery().getData());
        }

        @Override
        public BotState nextState() {
            return DESTINATION_PLACE_TEXT;
        }

    },

    DESTINATION_PLACE_TEXT(true, false) {
        BotState next;

        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendMessage(context, this, isQueryResponse(),
                    "Enter the destination place " + Emojis.MAG_RIGHT +
                            "\n (enter at least one letter and send it to see available places)");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {
            context.getUserData().setStateID(this.ordinal());

            context.getUserData().setStateID(this.ordinal());

            if (HandleInput.places(context) != null) {
                next = DESTINATION_PLACE_BUTTONS;
            } else {
                next = DESTINATION_PLACE_TEXT;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },
    DESTINATION_PLACE_BUTTONS(true, true) {
        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendSearchPlaces(context, HandleInput.places(context)
                    , "Select the arrival place");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {
            context.getUserData().setStateID(this.ordinal());
            context.getUserFlightData().setDestinationPlace(context.getCallbackQuery().getData());
        }

        @Override
        public BotState nextState() {
            return OUTBOUND_PARTIAL_DATE;
        }
    },

    OUTBOUND_PARTIAL_DATE(true, false) {
        BotState next;
        final IDatesValidator datesValidator = new DatesValidatorImpl(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendMessage(context, this, isQueryResponse(), Emojis.DATE + " Enter the outbound partial date (yyyy-mm-dd)");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {
            context.getUserData().setStateID(this.ordinal());

            if (datesValidator.isValid(context.getInput())) {
                context.getUserFlightData().setOutboundPartialDate(context.getInput());
                next = INBOUND_PARTIAL_DATE;
            } else next =OUTBOUND_PARTIAL_DATE;
        }
        @Override
        public BotState nextState() {
            return next;
        }
    },
    INBOUND_PARTIAL_DATE(true, true) {

        BotState next;
        final IDatesValidator datesValidator = new DatesValidatorImpl(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendMessage(context, this, isQueryResponse(), Emojis.DATE + " Enter the inbound partial date (yyyy-mm-dd) or tap on \"One way\"");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {

            if (context.getCallbackQuery() != null && context.getCallbackQuery().getData().equals("Button \"One way\" has been pressed")) {
                context.getUserFlightData().setInboundPartialDate(null);
                next=DATA_FILLED;
            } else {
                context.getUserData().setStateID(this.ordinal());

                if(datesValidator.isValid(context.getInput())){
                context.getUserFlightData().setInboundPartialDate(context.getInput());
                next=DATA_FILLED;
                } else next=INBOUND_PARTIAL_DATE;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },
    DATA_FILLED(true, true) {
        private BotState next;

        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendMessage(context, this, isQueryResponse(), "Tap on " + Emojis.PLANE + " to find current min price info");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {
            context.getUserData().setStateID(this.ordinal());
            if (context.getCallbackQuery().getData().equals("Button \"Find price\" has been pressed")) {
                sendQueryForPrice(context);
                //вызвать страницу скайсканера и сохранить мин цену в UserData
                next = DATA_TRANSFERRED;
            } else {
                next = DATA_FILLED;
            }
        }


        @Override
        public BotState nextState() {
            return next;
        }
    },
    DATA_TRANSFERRED(false, false) {
        BotState next;

        @Override
        public BotApiMethod<?> enter(BotStateContextRepo context) {
            if(!context.getUserFlightData().isRequestNotNull()){

//            if(context.getUserFlightData().getSkyScannerResponseQuotes()!=null || context.getUserFlightData().getSkyScannerResponseDates()!=null) {
//                return ResponseMessage.sendSearchResult(context, this);
                return ResponseMessage.sendErrorSearchResult(context,"Invalid input data");
            }
            return ResponseMessage.sendSearchResult(context, this);
        }

        @Override
        public void handleInput(BotStateContextRepo context) {

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
                next = SUBSCRIPT;
            } else {
                next = MAIN_MENU;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },
    SUBSCRIPT(false, false) {
        @Override
        public AnswerCallbackQuery enter(BotStateContextRepo context) {
            return ResponseMessage.sendSubConfirmation(context, "Search result was added to track list");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {

        }

        @Override
        public BotState nextState() {
            return SUBSCR_LIST;
        }
    },
    SUBSCR_LIST(true, false) {

        BotState next;

        @Override
        public BotApiMethod<?> enter(BotStateContextRepo context, List<UserSubscription> userSubscriptionList) {
            if (userSubscriptionList.size() > 0) {
                return ResponseMessage.sendSubscripList(context, userSubscriptionList);
            }
            return ResponseMessage.sendMessage(context, this, isQueryResponse(), "No subscriptions");
        }

        @Override
        public void handleInput(BotStateContextRepo context, UserSubscriptionDataService repository) {
            if (context.getInput().startsWith("/Delete_")) {
                repository.deleteSubscription(repository.findSubByChatId(context.getUserData().getChatId()).get(Integer.parseInt(context.getInput().substring(8))).getId());
                next = DELETE_SUBSCR;
            } else {
                next = SUBSCR_LIST_EDIT;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },
    SUBSCR_LIST_EDIT(true, false) {

        BotState next;

        @Override
        public BotApiMethod<?> enter(BotStateContextRepo context, List<UserSubscription> userSubscriptionList) {
            if (userSubscriptionList.size() > 0) {
                return ResponseMessage.sendSubscripListEdited(context, userSubscriptionList);
            }
            return ResponseMessage.sendMessage(context, this, isQueryResponse(), "No subscriptions");
        }

        @Override
        public void handleInput(BotStateContextRepo context, UserSubscriptionDataService repository) {
            if (context.getInput().startsWith("/Delete_")) {
                repository.deleteSubscription(repository.findSubByChatId(context.getUserData().getChatId()).get(Integer.parseInt(context.getInput().substring(8))).getId());
                next = DELETE_SUBSCR;
            } else {
                next = SUBSCR_LIST_EDIT;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },
    DELETE_SUBSCR(true, true) {

        @Override
        public AnswerCallbackQuery enter(BotStateContextRepo context) {
            return ResponseMessage.sendSubDeleting(context, "Search result was deleted from track list");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {

        }

        @Override
        public BotState nextState() {
            return SUBSCR_LIST;
        }
    },

    MAIN_MENU(true, false) {
        BotState next;

        @Override
        public SendMessage enter(BotStateContextRepo context) {
            return ResponseMessage.sendMessage(context, this, isQueryResponse(), "Main menu");
        }

        @Override
        public void handleInput(BotStateContextRepo context) {

            if (context.getInput().equals("Change localisation info")) {
                next = START;
            } else if (context.getInput().equals("New search")) {
                next = ORIGIN_PLACE_TEXT;
            } else {
                next = SUBSCR_LIST;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }
    };

    private static BotState[] states;
    private final boolean inputNeeded;
    private final boolean queryResponse;


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

    public boolean isQueryResponse() {
        return queryResponse;
    }

    public void handleInput (BotStateContextRepo context) {
    }

    public void handleInput(BotStateContextRepo context, UserSubscriptionDataService repository) {
    }

    public BotApiMethod<?> enter(BotStateContextRepo context) {
        return null;
    }

    public BotApiMethod<?> enter(BotStateContextRepo context, List<UserSubscription> userSubscriptionList) {
        return null;
    }


    public void sendQueryForPrice(BotStateContextRepo context) {

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

    public abstract BotState nextState(); //говорит в какое состояние переходить, когда текущее уже обработанно

}
