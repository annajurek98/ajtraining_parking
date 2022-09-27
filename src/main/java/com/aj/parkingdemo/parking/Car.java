package com.aj.parkingdemo.parking;

import com.aj.parkingdemo.parking.dto.CountryCode;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;

@Data
class Car {

    private final CountryCode countryOfOrigin;
    private final String licensePlate;
    private final Instant entryTime;
    private final int weightInKgs;


    public BigDecimal getParkingCost() {
        Duration duration = Duration.between(entryTime, Instant.now());
        long quantityHours = duration.toHours();
        double totalCost;
        if (duration.toMinutes() < 30) {
            return BigDecimal.ZERO;
        }
        if (weightInKgs < 600) {
            totalCost = 0.7 * (5 * quantityHours);
        } else if (weightInKgs < 3500) {
            totalCost = 1.0 * (5 * quantityHours);
        } else {
            totalCost = 2.0 * (5 * quantityHours);
        }
        return new BigDecimal(totalCost).setScale(2, RoundingMode.HALF_DOWN);
    }
}
