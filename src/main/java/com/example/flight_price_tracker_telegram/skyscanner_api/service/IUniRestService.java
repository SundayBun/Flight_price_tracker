package com.example.flight_price_tracker_telegram.skyscanner_api.service;


import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.HttpResponse;

/**
 * Service, which is manipulating with Rest calls.
 */

public interface IUniRestService {

    /**
     * Create GET request based on provided {@param path} with needed headers.
     *
     * @param path provided path with all the needed data
     * @return {@link HttpResponse<JsonNode>} response object.
     */

    HttpResponse<JsonNode> get(String path);
}
