package com.example.flight_price_tracker_telegram.skyscanner_api.dto.localisation;

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
