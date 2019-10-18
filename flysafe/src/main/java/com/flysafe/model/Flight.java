package com.flysafe.model;

import lombok.Data;

@Data
public class Flight {
    private String inbound;
    private String outbound;
    private double price;
}
