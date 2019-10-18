package com.flysafe.model;

import lombok.Data;

@Data
public class FlightRequest {

    private String country;
    private String currency;
    private String locale;
    private String originPlace;
    private String destinationPlace;
    private String outboundPartialDate;
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
