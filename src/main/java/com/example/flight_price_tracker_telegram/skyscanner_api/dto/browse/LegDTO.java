package com.example.flight_price_tracker_telegram.skyscanner_api.dto.browse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Data transfer object for "Leg".
 *
 */

@Data
public class LegDTO {

    @JsonProperty("CarrierIds")
    private List<Integer> carrierIds;

    @JsonProperty("OriginId")
    private Integer originId;

    @JsonProperty("DestinationId")
    private Integer destinationId;

    @JsonProperty("DepartureDate")
    private String departureDate;
}
