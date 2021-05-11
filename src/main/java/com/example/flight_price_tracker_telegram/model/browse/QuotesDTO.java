package com.example.flight_price_tracker_telegram.model.browse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Data transfer object for quotes.
 *
 */

@Data
public class QuotesDTO {

    @JsonProperty("QuoteId")
    private Integer quoteId;

    @JsonProperty("MinPrice")
    private Integer minPrice;

    @JsonProperty("Direct")
    private Boolean direct;

    @JsonProperty("OutboundLeg")
    private LegDTO outboundLeg;

    @JsonProperty("InboundLeg")
    private LegDTO inboundLeg;

    @JsonProperty("QuoteDateTime")
    private String quoteDateTime;
}
