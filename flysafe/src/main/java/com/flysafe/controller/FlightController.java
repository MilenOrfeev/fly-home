package com.flysafe.controller;

import com.flysafe.model.Flight;
import com.flysafe.model.FlightRequest;
import com.flysafe.service.FlightService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class FlightController {

    private FlightService flightService;

    public FlightController() {
        flightService = new FlightService();
    }

    @PostMapping(path = "/flight")
    public Flight getFlightData(@RequestBody FlightRequest flightRequest) { //FIXME change return type to Flight

        return flightService.findFlights(flightRequest);
    }
}
