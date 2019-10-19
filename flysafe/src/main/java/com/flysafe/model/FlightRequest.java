package com.flysafe.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlightRequest {

    @ApiModelProperty(example = "AE")
    private String country;
    @ApiModelProperty(example = "USD")
    private String currency;
    @ApiModelProperty(example = "en-EN")
    private String locale;
    @ApiModelProperty(example = "PARI-sky")
    private String originPlace;
    @ApiModelProperty(example = "anywhere")
    private String destinationPlace;
    @ApiModelProperty(example = "anytime")
    private String outboundPartialDate;
    @ApiModelProperty(example = "")
    private String inboundPartialDate;

    public FlightRequest(String country, String currency, String locale, String originPlace, String destinationPlace, String outboundPartialDate, String inboundPartialDate) {
        this.country = country;
        this.currency = currency;
        this.locale = locale;
        this.originPlace = originPlace;
        this.destinationPlace = destinationPlace;
        this.outboundPartialDate = outboundPartialDate;
        this.inboundPartialDate = inboundPartialDate;
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
}
