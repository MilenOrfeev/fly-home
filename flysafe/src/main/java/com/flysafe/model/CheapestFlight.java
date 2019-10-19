package com.flysafe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheapestFlight {
    String origin;
    String destination;
    double price;
    String currency;
}
