package com.example.flight_price_tracker_telegram.model.localisation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Data transfer object for Country.
 */

@Data
public class CountryDTO {

    @JsonProperty("Code")
    private String code;

    @JsonProperty("Name")
    private String name;

}
