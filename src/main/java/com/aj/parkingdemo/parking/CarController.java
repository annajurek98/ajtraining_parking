package com.aj.parkingdemo.parking;


import com.aj.parkingdemo.parking.dto.CountryCode;
import com.aj.parkingdemo.parking.dto.CarCheckinDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Instant;

@Slf4j
@Controller
class CarController {

    private final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @PostMapping(value = "/car")
    public @ResponseBody
    Car createCar(@RequestBody CarCheckinDTO checkinDTO) {
        log.info("Creating car for checkinDTO={}", checkinDTO);
        return carRepository.checkIn(checkinDTO.getCountryOfOrigin(), checkinDTO.getLicensePlate(), checkinDTO.getWeightInKgs());
    }

    @GetMapping(value = "/car/licensePlate/{licensePlate}/countryCode/{countryCode}")
    public @ResponseBody
    Car getCarByLicensePlateAndCountry(@PathVariable String licensePlate, @PathVariable CountryCode countryCode) {
        log.info("Searching car for licensePlate={} countrycode={}", licensePlate, countryCode);
        return carRepository.getCar(countryCode, licensePlate);
    }

    @DeleteMapping(value = "/car/licensePlate/{licensePlate}/countryCode/{countryCode}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void
    deleteCarByLicensePlateAndCountry(@PathVariable String licensePlate, @PathVariable CountryCode countryCode) {
        carRepository.checkOut(countryCode, licensePlate);
    }
}
