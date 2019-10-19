
package com.flysafe.skyscanner;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "PlaceId",
    "Name",
    "Type",
    "SkyscannerCode"
})
public class Place {

    @JsonProperty("PlaceId")
    private Integer placeId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("SkyscannerCode")
    private String skyscannerCode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("PlaceId")
    public Integer getPlaceId() {
        return placeId;
    }

    @JsonProperty("PlaceId")
    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Type")
    public String getType() {
        return type;
    }

    @JsonProperty("Type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("SkyscannerCode")
    public String getSkyscannerCode() {
        return skyscannerCode;
    }

    @JsonProperty("SkyscannerCode")
    public void setSkyscannerCode(String skyscannerCode) {
        this.skyscannerCode = skyscannerCode;
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
