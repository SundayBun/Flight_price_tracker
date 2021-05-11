package com.example.flight_price_tracker_telegram.model.browse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Data transfer object for Carriers.
 *
 */

@Data
public class CarriersDTO {

    @JsonProperty("CarrierId")
    private Integer carrierId;

    @JsonProperty("Name")
    private Integer name;
}
