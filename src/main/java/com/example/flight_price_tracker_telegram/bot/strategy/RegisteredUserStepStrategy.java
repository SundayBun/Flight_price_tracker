package com.example.flight_price_tracker_telegram.bot.strategy;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.states.*;
import com.example.flight_price_tracker_telegram.bot.statesMethodSequence.JustEnterMethod;
import com.example.flight_price_tracker_telegram.bot.statesMethodSequence.RegularSequence;
import com.example.flight_price_tracker_telegram.bot.statesMethodSequence.SequenceWithRepo;
import com.example.flight_price_tracker_telegram.bot.statesMethodSequence.StatesMethodSequence;
import com.example.flight_price_tracker_telegram.bot.validation.IInputValidator;
import com.example.flight_price_tracker_telegram.bot.validation.InputValidatorImpl;
import com.example.flight_price_tracker_telegram.repository.UserSubscriptionDataService;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EqualsAndHashCode
public class RegisteredUserStepStrategy implements IStepStrategy {


    private Context context;
    private Update update;
    private StatesMethodSequence statesMethodSequence;
    private final UserSubscriptionDataService repository;
    private final IInputValidator inputValidator = new InputValidatorImpl();

    public RegisteredUserStepStrategy(Context context, Update update, UserSubscriptionDataService repository) {
        this.context = context;
        this.update = update;
        this.repository = repository;
    }

    @Override
    public Context getContext() {
        return statesMethodSequence.getContext();
    }

    @Override
    public BotApiMethod<?> doStateLogic() {

        log.info("UserData before doStateLogic(): chatID:{}, state: {}, country{}, currency{}, locale {}",
                context.getUserData().getChatId(), context.getUserData().getStateName(), context.getUserData().getCountry(), context.getUserData().getCurrency(), context.getUserData().getLocale());
        log.info("Context before doStateLogic(): state {}", context.getState().getStateName());

        checkIfButtonCommand();

        log.info("step 1 UserData after checkIfButtonCommand(): chatID:{}, stateName: {}, country{}, currency{}, locale {}",
                context.getUserData().getChatId(), context.getUserData().getStateName(), context.getUserData().getCountry(), context.getUserData().getCurrency(), context.getUserData().getLocale());
        log.info("step 1 Context after after checkIfButtonCommand(): state {}", context.getState().getStateName());

        getStateFromUserData();

        log.info("step 2 UserData after getStateFromUserData(context): chatID:{}, stateName: {}, country{}, currency{}, locale {}",
                context.getUserData().getChatId(), context.getUserData().getStateName(), context.getUserData().getCountry(), context.getUserData().getCurrency(), context.getUserData().getLocale());
        log.info("step 2 Context after getStateFromUserData(context): state {}", context.getState().getStateName());


        if (inputValidator.isValidInput(update, context)) {
            log.info("Valid inputData");

            if (isSubscrState(context)) {
                statesMethodSequence = new SequenceWithRepo(context, repository);
                log.info("Regular method sequence with repo");
            } else {
                statesMethodSequence = new RegularSequence(context);
                log.info("Regular method sequence");
            }
        } else {
            log.info("Invalid inputData");
            statesMethodSequence = new JustEnterMethod(context);
            log.info("Just enter method");
        }

        return statesMethodSequence.getStatesMethodSequence();
    }

    public void checkIfButtonCommand() {

        Map<String, StateName> commands = new HashMap<>();


        commands.put("Button \"ENG\" has been pressed", StateName.START);
        commands.put("Button \"RUS\" has been pressed", StateName.START);
        commands.put("Button \"Find price\" has been pressed", StateName.DATA_FILLED);
        commands.put("USD", StateName.CURRENCY);
        commands.put("EUR", StateName.CURRENCY);
        commands.put("RUB", StateName.CURRENCY);
        commands.put("Button \"Track it\" has been pressed", StateName.DATA_TRANSFERRED);
        commands.put("Button \"One way\" has been pressed", StateName.INBOUND_PARTIAL_DATE);
        //  commands.put("Delete", BotState.SUBSCR_LIST_EDIT);
        commands.put("Региональные настройки", StateName.START);
        commands.put("Change localisation info", StateName.START);
        commands.put("Новый поиск", StateName.MAIN_MENU);
        commands.put("New search", StateName.MAIN_MENU);
        commands.put("Подписки", StateName.SUBSCR_LIST);
        commands.put("See your track list", StateName.SUBSCR_LIST);
        commands.put("/start", StateName.START);

        if (update.hasMessage() && commands.containsKey(update.getMessage().getText())) {
            context.getUserData().setStateName(commands.get(update.getMessage().getText()));
        }

        if (update.hasCallbackQuery() && commands.containsKey(update.getCallbackQuery().getData())) {
            context.getUserData().setStateName(commands.get(update.getCallbackQuery().getData()));
        }
    }

    public void getStateFromUserData() {

        log.info("step ** Context before getStateFromUserData(context): state {}", context.getUserData().getStateName());
        switch (context.getUserData().getStateName()) {
            case START: {
                context.setState(new StartState(context));
                break;
            }
            case COUNTRY_TEXT: {
                context.setState(new CountryTextState(context));
                break;
            }
            case COUNTRY_BUTTONS: {
                context.setState(new CountryButtonState(context));
                break;
            }
            case CURRENCY: {
                context.setState(new CurrencyState(context));
                break;
            }
            case ORIGIN_PLACE_TEXT: {
                context.setState(new OriginPlaceText(context));
                break;
            }
            case ORIGIN_PLACE_BUTTONS: {
                context.setState(new OriginPlaceButtons(context));
                break;
            }
            case DESTINATION_PLACE_TEXT: {
                context.setState(new DestinationPlaceTextState(context));
                break;
            }
            case DESTINATION_PLACE_BUTTONS: {
                context.setState(new DestinationPlaceButtonsState(context));
                break;
            }
            case OUTBOUND_PARTIAL_DATE: {
                context.setState(new OutBoundPartialDatesState(context));
                break;
            }
            case INBOUND_PARTIAL_DATE: {
                context.setState(new InboundPartialDateState(context));
                break;
            }
            case DATA_FILLED: {
                context.setState(new DataFilledState(context));
                break;
            }
            case DATA_TRANSFERRED: {
                context.setState(new DataTransferredState(context));
                break;
            }
            case SUBSCR_LIST: {
                context.setState(new SubscriptionListState(context));
                break;
            }
            case SUBSCRIPT: {
                context.setState(new SubscriptionState(context));
                break;
            }
            case MAIN_MENU: {
                context.setState(new MainMenuState(context));
                break;
            }
            default:
                context.setState(new StartState(context));
                log.info("step ** Context after getStateFromUserData(context): state {}", context.getState());
                break;
        }
    }

    private boolean isSubscrState(Context context) {
        return context.getUserData().getStateName().equals(StateName.SUBSCR_LIST);
    }

    public void setStatesMethodSequence(StatesMethodSequence statesMethodSequence) {
        this.statesMethodSequence = statesMethodSequence;
    }
}
