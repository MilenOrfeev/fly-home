package com.flysafe.skyscanner;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FlightResponse {
    @JsonProperty("Quotes")
    private List<Quote> quotes;
    @JsonProperty("Places")
    private List<Place> places;
    @JsonProperty("Carriers")
    private List<Carrier> carriers;
    @JsonProperty("Currencies")
    private List<Currency> currencies;
}
