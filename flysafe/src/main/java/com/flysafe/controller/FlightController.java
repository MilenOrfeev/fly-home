package com.flysafe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flysafe.model.CheapestFlight;
import com.flysafe.model.FlightRequest;
import com.flysafe.service.FlightService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1")
public class FlightController {

    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping(path = "/flight")
    public CheapestFlight getFlightData(@RequestBody FlightRequest flightRequest) throws JsonProcessingException, ParseException {

        return flightService.findFlights( flightRequest );
    }
}
