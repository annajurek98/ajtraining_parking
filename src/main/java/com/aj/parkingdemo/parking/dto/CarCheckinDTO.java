package com.aj.parkingdemo.parking.dto;

import lombok.Data;

@Data
public class CarCheckinDTO {

    private CountryCode countryOfOrigin;
    private String licensePlate;
    private int weightInKgs;
}
