package com.example.flight_price_tracker_telegram.bot.states;

import java.io.Serializable;

public enum StateName implements Serializable {
    START,
    COUNTRY_TEXT,
    COUNTRY_BUTTONS,
    CURRENCY,
    ORIGIN_PLACE_TEXT,
    ORIGIN_PLACE_BUTTONS,
    DESTINATION_PLACE_TEXT,
    DESTINATION_PLACE_BUTTONS,
    OUTBOUND_PARTIAL_DATE,
    INBOUND_PARTIAL_DATE,
    DATA_FILLED,
    DATA_TRANSFERRED,
    SUBSCRIPT,
    SUBSCR_LIST,
    DELETE_SUBSCR,
    MAIN_MENU;

}
