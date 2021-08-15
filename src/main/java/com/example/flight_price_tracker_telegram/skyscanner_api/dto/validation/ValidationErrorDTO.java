package com.example.flight_price_tracker_telegram.skyscanner_api.dto.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ValidationErrorDTO {

//    @JsonProperty("ParameterName")
//    private String parameterName;
//
//    @JsonProperty("ParameterValue")
//    private String parameterValue;

    @JsonProperty("message")
    private String message;
}
