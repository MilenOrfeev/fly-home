package com.flysafe;

import com.flysafe.controller.FlightController;
import com.flysafe.model.FlightRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlysafeApplication {

	public static void main(String[] args) {

		SpringApplication.run(FlysafeApplication.class, args);

        FlightController flightController = new FlightController();
        FlightRequest flightRequest = new FlightRequest( "AE", "USD", "bg-BG", "PARI-sky",
                 "anywhere", "anytime", "");
        flightController.getFlightData( flightRequest );

	}

}
