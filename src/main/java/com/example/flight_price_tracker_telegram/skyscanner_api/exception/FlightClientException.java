package com.example.flight_price_tracker_telegram.skyscanner_api.exception;

import com.example.flight_price_tracker_telegram.skyscanner_api.dto.validation.ValidationErrorDTO;

import java.util.List;

public final class FlightClientException extends  RuntimeException  {

    private List<ValidationErrorDTO> validationErrorDtos;

    public FlightClientException(String message) {
        super(message);
    }

    public FlightClientException(String message, Throwable throwable) {
        super(message, throwable);
    }
    public FlightClientException(String message, List<ValidationErrorDTO> errors) {
        super(message);
        this.validationErrorDtos = errors;
    }
}
