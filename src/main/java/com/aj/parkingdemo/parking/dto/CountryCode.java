package com.aj.parkingdemo.parking.dto;

public enum CountryCode {

    PL,
    GE,
    US,
    UK;

    public static CountryCode fromString(String countryCode) {
        for (CountryCode value : CountryCode.values()) {
            if (value.name().equals(countryCode)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Could not find country code!");
    }
}
