package com.flysafe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheapestFlight {

    String origin;
    String destination;
    Date departureDate;
    Date returnDate;
    double price;
    String currency;
}
