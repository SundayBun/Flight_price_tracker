package com.example.flight_price_tracker_telegram.bot.states;

import com.example.flight_price_tracker_telegram.bot.Context;
import com.example.flight_price_tracker_telegram.bot.service.ResponseMessage;
import com.example.flight_price_tracker_telegram.repository.entity.UserSubscription;
import com.example.flight_price_tracker_telegram.repository.UserSubscriptionDataService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.List;
import java.util.Locale;

@Slf4j
public class SubscriptionListState extends State {

    private boolean deleteItem = false;
    private int pageNumber = 0;

    public SubscriptionListState(Context context) {
        super(context);
        this.textMessageRequest = true;
        this.queryResponse = true;
        this.stateName = StateName.SUBSCR_LIST;
        localeMessageService.setLocale(Locale.forLanguageTag(context.getUserData().getLocale()));
    }

    @Override
    public BotApiMethod<?> enter(Context context, List<UserSubscription> userSubscriptionList) {

        log.info("deleteItem: {}", deleteItem);

        if (deleteItem) {
            return ResponseMessage.sendSubDeleting(context, localeMessageService.getMessage("state.subscriptionListDel"));
        }

        if (userSubscriptionList.size() > 0) {

            if (context.getCallbackQuery() != null && isNumeric(context.getCallbackQuery().getData()) && Integer.parseInt(context.getCallbackQuery().getData()) < userSubscriptionList.size()) {
                pageNumber = Integer.parseInt(context.getCallbackQuery().getData());
            }

            if (userSubscriptionList.get(pageNumber).getUserFlightData().getInboundPartialDate() == null) {
                return ResponseMessage.sendSubscripList(context, userSubscriptionList, pageNumber, localeMessageService.getMessage("state.subscriptionListOneWay",
                        ResponseMessage.getPlaceNameFromDTO(userSubscriptionList.get(pageNumber).getSkyScannerResponseQuotes().getPlaces(), userSubscriptionList.get(pageNumber).getUserFlightData().getOriginPlace()),
                        ResponseMessage.getPlaceNameFromDTO(userSubscriptionList.get(pageNumber).getSkyScannerResponseQuotes().getPlaces(), userSubscriptionList.get(pageNumber).getUserFlightData().getDestinationPlace()),
                        ResponseMessage.getDate(userSubscriptionList.get(pageNumber).getUserFlightData().getOutboundPartialDate(), userSubscriptionList.get(pageNumber).getUserData().getLocale()),
                        userSubscriptionList.get(pageNumber).getSkyScannerResponseQuotes().getQuotes().get(0).getMinPrice(),
                        userSubscriptionList.get(pageNumber).getSkyScannerResponseQuotes().getCurrencies().get(0).getSymbol(),
                        userSubscriptionList.get(pageNumber).getUserFlightData().getSkyScannerResponseQuotes().getCarriers(),
                        pageNumber));
            }

            return ResponseMessage.sendSubscripList(context, userSubscriptionList, pageNumber, localeMessageService.getMessage("state.subscriptionListTwoWays",
                    ResponseMessage.getPlaceNameFromDTO(userSubscriptionList.get(pageNumber).getSkyScannerResponseDates().getPlaces(), userSubscriptionList.get(pageNumber).getUserFlightData().getOriginPlace()),
                    ResponseMessage.getPlaceNameFromDTO(userSubscriptionList.get(pageNumber).getSkyScannerResponseDates().getPlaces(), userSubscriptionList.get(pageNumber).getUserFlightData().getDestinationPlace()),
                    ResponseMessage.getDate(userSubscriptionList.get(pageNumber).getUserFlightData().getOutboundPartialDate(), userSubscriptionList.get(pageNumber).getUserData().getLocale()),
                    ResponseMessage.getDate(userSubscriptionList.get(pageNumber).getUserFlightData().getInboundPartialDate(), userSubscriptionList.get(pageNumber).getUserData().getLocale()),
                    userSubscriptionList.get(pageNumber).getSkyScannerResponseDates().getQuotes().get(0).getMinPrice(),
                    userSubscriptionList.get(pageNumber).getSkyScannerResponseDates().getCurrencies().get(0).getSymbol(),
                    userSubscriptionList.get(pageNumber).getSkyScannerResponseDates().getCarriers(),
                    pageNumber));
        }

        return ResponseMessage.sendMessage(context, localeMessageService.getMessage("state.subscriptionListNoSub"), null);

    }

    @Override
    public void handleInput(Context context, UserSubscriptionDataService repository) throws NullPointerException {
        context.getUserData().setStateName(stateName);
        if (context.getInput().matches("/Delete_" + "-?\\d+")) {
            try {
                repository.deleteSubscription(repository.findSubByChatId(context.getUserData().getChatId()).get(Integer.parseInt(context.getInput().substring(8))).getId());
                deleteItem = true;

            } catch (IndexOutOfBoundsException exp) {
                deleteItem = false;
            }
            log.info("deleteItem: {}", deleteItem);
        }
    }

    @Override
    public State nextState() {
        return null;
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+");
    }

}
