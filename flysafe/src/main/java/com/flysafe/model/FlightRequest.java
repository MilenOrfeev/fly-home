package com.flysafe.model;

import lombok.Data;

@Data
public class FlightRequest {
    private String inbound;
    private String outbound;
}
