package com.aj.parkingdemo.parking;

import com.aj.parkingdemo.parking.dto.CountryCode;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.assertEquals;

public class CarTest {

    @Test
    public void getParkingCost_returnsBeneficialCost() {
        //given
        Instant entryTime = LocalDateTime.of(2022, 5, 5, 19, 13).atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        Car subject = new Car(CountryCode.GE, "AB 12345", entryTime, 400);

        Instant entryTime2 = LocalDateTime.of(2022, 5, 9, 16, 30).atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        Car subject2 = new Car(CountryCode.PL, "WPP 54127", entryTime2, 3200);

        Instant entryTime3 = LocalDateTime.of(2022, 5, 10, 20, 10).atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        Car subject3 = new Car(CountryCode.PL, "WPP 54412", entryTime3, 3700);

        Instant entryTime4 = LocalDateTime.of(2022, 5, 7, 9, 30).atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        Car subject4 = new Car(CountryCode.GE, "WT 12345", entryTime4, 3750);


        // when
        BigDecimal result = subject.getParkingCost();
        BigDecimal result2 = subject2.getParkingCost();
        BigDecimal result3 = subject3.getParkingCost();
        BigDecimal result4 = subject4.getParkingCost();

        // then

        assertEquals(new BigDecimal("3.50"), result);
        assertEquals(new BigDecimal("1.45"), result2);
        assertEquals(new BigDecimal("6.00"), result3);
        assertEquals(new BigDecimal("0.25"), result4);


    }


}
