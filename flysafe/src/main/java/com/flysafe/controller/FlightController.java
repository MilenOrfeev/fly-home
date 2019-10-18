package com.flysafe.controller;

import com.flysafe.model.Flight;
import com.flysafe.model.FlightRequest;
import com.flysafe.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FlightController {

    private FlightService flightService;

    public FlightController() {
        flightService = new FlightService();
    }

    @GetMapping(value = "/flight")
    public String getFlightData(FlightRequest flightRequest) { //FIXME change return type to Flight

        return flightService.findFlights(flightRequest);
    }
}
