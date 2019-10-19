package com.flysafe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flysafe.model.FlightRequest;
import com.flysafe.service.FlightService;
import com.flysafe.skyscanner.FlightResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class FlightController {

    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping(path = "/flight")
    public FlightResponse getFlightData(@RequestBody FlightRequest flightRequest) throws JsonProcessingException {

        return flightService.findFlights(flightRequest);
    }
}
