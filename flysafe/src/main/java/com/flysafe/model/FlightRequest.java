package com.flysafe.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlightRequest {

    @ApiModelProperty(example = "UK")
    private String country;
    @ApiModelProperty(example = "GBP")
    private String currency;
    @ApiModelProperty(example = "en-EN")
    private String locale;
    @ApiModelProperty(example = "PARI-sky")
    private String originPlace;
    @ApiModelProperty(example = "anywhere")
    private String destinationPlace;
    @ApiModelProperty(example = "anytime")
    private String outboundPartialDate;
    @ApiModelProperty(example = "anytime")
    private String inboundPartialDate;
    @ApiModelProperty(example = "0")
    private int range;
    @ApiModelProperty(example = "0")
    private int maxPrice;

    public FlightRequest(String country, String currency, String locale, String originPlace, String destinationPlace, String outboundPartialDate, String inboundPartialDate, int range, int maxPrice) {
        this.country = country;
        this.currency = currency;
        this.locale = locale;
        this.originPlace = originPlace;
        this.destinationPlace = destinationPlace;
        this.outboundPartialDate = outboundPartialDate;
        this.inboundPartialDate = inboundPartialDate;
        this.range = range;
        this.maxPrice = maxPrice;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }

    public String getLocale() {
        return locale;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public String getDestinationPlace() {
        return destinationPlace;
    }

    public String getOutboundPartialDate() {
        return outboundPartialDate;
    }

    public String getInboundPartialDate() {
        return inboundPartialDate;
    }

    public int getRange() {
        return range;
    }

    public int getMaxPrice() {
        return maxPrice;
    }
}
