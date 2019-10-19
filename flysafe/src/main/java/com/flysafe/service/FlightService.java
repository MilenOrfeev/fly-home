package com.flysafe.service;

import com.flysafe.model.Flight;
import com.flysafe.model.FlightRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static com.flysafe.config.KeysConfig.API_KEY;
import static com.flysafe.config.KeysConfig.skyscannerURL;

@Service
public class FlightService {

    public Flight findFlights(FlightRequest flightRequest) { //FIXME change return type to flight

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept( Collections.singletonList( MediaType.APPLICATION_JSON ) );
        headers.set( "api-key", API_KEY );

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        String url = skyscannerURL + "flights/browse/browsequotes/v1.0/" +
                flightRequest.getCountry() + "/" +
                flightRequest.getCurrency() + "/" +
                flightRequest.getLocale() + "/" +
                flightRequest.getOriginPlace() + "/" +
                flightRequest.getDestinationPlace() + "/" +
                flightRequest.getOutboundPartialDate() + "/" +
                flightRequest.getInboundPartialDate();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        return new Flight();
    }

}
