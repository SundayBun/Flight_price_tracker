package com.example.flight_price_tracker_telegram.model.browse;

import com.example.flight_price_tracker_telegram.model.localisation.CurrencyDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Data transfer object for FlightPrices.
 *
 */
@Data
public class FlightPricesDTO {

    @JsonProperty("Quotes")
    private List<QuotesDTO> quotes;

    @JsonProperty("Places")
    private List<BrowsePlacesDTO> places;

    @JsonProperty("Carriers")
    private List<CarriersDTO> carriers;

    @JsonProperty("Currencies")
    private List<CurrencyDTO> currencies;


}
