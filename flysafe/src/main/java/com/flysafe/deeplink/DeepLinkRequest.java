package com.flysafe.deeplink;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeepLinkRequest {
    String country;
    String currency;
    String locale;
    String originPlace;
    String destinationPlace;
    String locationSchema;
    String outboundDate;
    String inboundDate;
    int adults;

}
