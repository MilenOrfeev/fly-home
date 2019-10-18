package com.flysafe.controller;

import com.flysafe.model.Flight;
import com.flysafe.model.FlightRequest;
import com.flysafe.service.FlightService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FlightController {
    private FlightService flightService;

    @GetMapping(value = "flight")
    public Flight getFlightData(FlightRequest flightRequest) {
        return flightService.findFlights(flightRequest);
    }
}
