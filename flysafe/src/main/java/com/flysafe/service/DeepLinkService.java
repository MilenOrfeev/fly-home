package com.flysafe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flysafe.deeplink.DeepLinkRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static com.flysafe.config.KeysConfig.API_KEY;

@Service
public class DeepLinkService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String getDeepLink(String originName, String destinationName, String departureDate, String returnDate) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept( Collections.singletonList( MediaType.APPLICATION_JSON));
        headers.set( "api-key", API_KEY );

        DeepLinkRequest request = createBody(originName, destinationName, departureDate, returnDate);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(request));

        HttpEntity<DeepLinkRequest> entity = new HttpEntity<>(request, headers);

        String url = "https://www.skyscanner.net/g/chiron/api/v1/flights/search/pricing/v1.0";

        logger.info("Doing request with url {}", url);

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        } catch (HttpServerErrorException.InternalServerError e) {
            logger.error(e.getMessage(), e);
        }

        logger.info("Received {}" + response.getBody());

        return response.getBody();
    }

    private DeepLinkRequest createBody(String originName, String destinationName, String departureDate, String returnDate) {
        DeepLinkRequest request = new DeepLinkRequest();
        request.setOriginPlace(originName);
        request.setDestinationPlace(destinationName);
        request.setOutboundDate(departureDate.split("T")[0]);
        request.setInboundDate(returnDate.split("T")[0]);

        // Hardcoded
        request.setAdults(1);
        request.setCurrency("GBP");
        request.setCountry("UK");
        request.setLocationSchema("iata");
        request.setLocale("en-EN");

        return request;
    }
}
