package com.flysafe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flysafe.model.CheapestFlight;
import com.flysafe.model.FlightRequest;
import com.flysafe.skyscanner.FlightResponse;
import com.flysafe.skyscanner.Place;
import com.flysafe.skyscanner.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import static com.flysafe.config.KeysConfig.API_KEY;
import static com.flysafe.config.KeysConfig.skyscannerURL;

@Service
public class FlightService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ObjectMapper objectMapper;

    public FlightService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public CheapestFlight findFlights(FlightRequest flightRequest) throws JsonProcessingException {

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

        logger.info("Doing request with url {}", url);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        logger.info("Status code is {}", response.getStatusCode());
        logger.info("Response body is {}", response.getBody());

        if (response.getBody() == null) {
            return new CheapestFlight();
        }

        FlightResponse flightResponse = objectMapper.readValue(response.getBody(), FlightResponse.class);
        logger.info("Created object is {}", flightResponse);

        HashMap<Integer, Place> placeToId = new HashMap<>();
        for (Place place : flightResponse.getPlaces()) {
            placeToId.put(place.getPlaceId(), place);
        }

        Quote minPriceQuote = Collections.min(flightResponse.getQuotes(), Comparator.comparing(Quote::getMinPrice));
        String originName = placeToId.get(minPriceQuote.getOutboundLeg().getOriginId()).getName();
        String destinationName = placeToId.get(minPriceQuote.getInboundLeg().getOriginId()).getName();
        logger.info("The cheapest flight goes from {} to {} and costs {} {}", originName, destinationName
                , minPriceQuote.getMinPrice(), flightRequest.getCurrency());

        return new CheapestFlight(originName, destinationName, minPriceQuote.getMinPrice(), flightRequest.getCurrency());
    }

}
