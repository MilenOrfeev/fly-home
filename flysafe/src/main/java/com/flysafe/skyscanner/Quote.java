
package com.flysafe.skyscanner;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "QuoteId",
    "MinPrice",
    "Direct",
    "OutboundLeg",
    "InboundLeg",
    "QuoteDateTime"
})
public class Quote {

    @JsonProperty("QuoteId")
    private Integer quoteId;
    @JsonProperty("MinPrice")
    private Integer minPrice;
    @JsonProperty("Direct")
    private Boolean direct;
    @JsonProperty("OutboundLeg")
    private OutboundLeg outboundLeg;
    @JsonProperty("InboundLeg")
    private InboundLeg inboundLeg;
    @JsonProperty("QuoteDateTime")
    private String quoteDateTime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("QuoteId")
    public Integer getQuoteId() {
        return quoteId;
    }

    @JsonProperty("QuoteId")
    public void setQuoteId(Integer quoteId) {
        this.quoteId = quoteId;
    }

    @JsonProperty("MinPrice")
    public Integer getMinPrice() {
        return minPrice;
    }

    @JsonProperty("MinPrice")
    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    @JsonProperty("Direct")
    public Boolean getDirect() {
        return direct;
    }

    @JsonProperty("Direct")
    public void setDirect(Boolean direct) {
        this.direct = direct;
    }

    @JsonProperty("OutboundLeg")
    public OutboundLeg getOutboundLeg() {
        return outboundLeg;
    }

    @JsonProperty("OutboundLeg")
    public void setOutboundLeg(OutboundLeg outboundLeg) {
        this.outboundLeg = outboundLeg;
    }

    @JsonProperty("InboundLeg")
    public InboundLeg getInboundLeg() {
        return inboundLeg;
    }

    @JsonProperty("InboundLeg")
    public void setInboundLeg(InboundLeg inboundLeg) {
        this.inboundLeg = inboundLeg;
    }

    @JsonProperty("QuoteDateTime")
    public String getQuoteDateTime() {
        return quoteDateTime;
    }

    @JsonProperty("QuoteDateTime")
    public void setQuoteDateTime(String quoteDateTime) {
        this.quoteDateTime = quoteDateTime;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
