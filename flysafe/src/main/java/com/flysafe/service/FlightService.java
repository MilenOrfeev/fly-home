package com.flysafe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flysafe.model.CheapestFlight;
import com.flysafe.model.FlightRequest;
import com.flysafe.model.SpecificFlightRequest;
import com.flysafe.skyscanner.FlightResponse;
import com.flysafe.skyscanner.Place;
import com.flysafe.skyscanner.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.flysafe.config.KeysConfig.API_KEY;
import static com.flysafe.config.KeysConfig.skyscannerURL;

@Service
public class FlightService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ObjectMapper objectMapper;
    RestTemplate restTemplate;

    public FlightService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public CheapestFlight findFlights(FlightRequest flightRequest) throws JsonProcessingException, ParseException {

        String responseBody = requestTemplate( flightRequest );

        if (responseBody == null) {
            return new CheapestFlight();
        }

        FlightResponse flightResponse = objectMapper.readValue(responseBody, FlightResponse.class);
        logger.info("Created object is {}", flightResponse);

        HashMap<Integer, Place> placeToId = new HashMap<>();
        for (Place place : flightResponse.getPlaces()) {
            placeToId.put(place.getPlaceId(), place);
        }

        Quote minPriceQuote = Collections.min(flightResponse.getQuotes(), Comparator.comparing(Quote::getMinPrice));
        String originName = placeToId.get(minPriceQuote.getOutboundLeg().getOriginId()).getName();
        String destinationName = placeToId.get(minPriceQuote.getInboundLeg().getOriginId()).getName();

        String departureDateString = minPriceQuote.getOutboundLeg().getDepartureDate();
        String returnDateString = minPriceQuote.getInboundLeg().getDepartureDate();

        DateFormat format = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );
        Date departureDate = format.parse( departureDateString );
        Date returnDate = format.parse( returnDateString );

        logger.info("The cheapest flight goes from {} to {} and costs {} {}", originName, destinationName
                , minPriceQuote.getMinPrice(), flightRequest.getCurrency());

        return new CheapestFlight( originName, destinationName, departureDate, returnDate,
                minPriceQuote.getMinPrice(), flightRequest.getCurrency() );
    }

    public CheapestFlight findFlightsInRange(SpecificFlightRequest specificFlightRequest) throws JsonProcessingException, ParseException {

        String responseBody = requestTemplate( specificFlightRequest );

        if (responseBody == null) {
            return new CheapestFlight();
        }

        int range = specificFlightRequest.getRange();
        int maxPrice = specificFlightRequest.getMaxPrice();

        FlightResponse flightResponse = objectMapper.readValue(responseBody, FlightResponse.class);
        logger.info("Created object is {}", flightResponse);

        Map<Integer, Place> placeToId = new HashMap<>();
        for (Place place : flightResponse.getPlaces()) {
            placeToId.put(place.getPlaceId(), place);
        }

        while (true) {

            if (flightResponse.getQuotes().size() <= 1 ) return null;

            Quote minPriceQuote = Collections.min( flightResponse.getQuotes(), Comparator.comparing( Quote::getMinPrice ) );

            String departureDateString = minPriceQuote.getOutboundLeg().getDepartureDate();
            String returnDateString = minPriceQuote.getInboundLeg().getDepartureDate();

            DateFormat format = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );
            Date departureDate = format.parse( departureDateString );
            Date returnDate = format.parse( returnDateString );

            long diffInMillies = Math.abs( returnDate.getTime() - departureDate.getTime() );
            long diff = TimeUnit.DAYS.convert( diffInMillies, TimeUnit.MILLISECONDS );

            if (diff == range && minPriceQuote.getMinPrice() <= maxPrice) {
                String originName = placeToId.get( minPriceQuote.getOutboundLeg().getOriginId() ).getName();
                String destinationName = placeToId.get( minPriceQuote.getInboundLeg().getOriginId() ).getName();

                logger.info( "The cheapest flight goes from {} at {} to {} and returns at {}, and costs {} {}", originName, departureDate,
                        destinationName, returnDate, minPriceQuote.getMinPrice(), specificFlightRequest.getCurrency() );

                return new CheapestFlight( originName, destinationName, departureDate, returnDate,
                        minPriceQuote.getMinPrice(), specificFlightRequest.getCurrency() );
            } else flightResponse.getQuotes().remove( minPriceQuote );
        }
    }

    private String requestTemplate(FlightRequest flightRequest) {
        restTemplate = new RestTemplate();

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

        return response.getBody();
    }

    private String requestTemplate(SpecificFlightRequest flightRequest) {
        restTemplate = new RestTemplate();

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

        return response.getBody();
    }

}
