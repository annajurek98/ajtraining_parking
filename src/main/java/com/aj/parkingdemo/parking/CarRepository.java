package com.aj.parkingdemo.parking;

import com.aj.parkingdemo.parking.dto.CountryCode;

interface CarRepository {

    Car checkIn(CountryCode countryCode, String licensePlate, int weightInKgs);

    Car getCar(CountryCode countryCode, String licensePlate);

    void checkOut(CountryCode countryCode, String licensePlate);
}
