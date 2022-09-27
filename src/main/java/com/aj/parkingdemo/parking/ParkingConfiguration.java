package com.aj.parkingdemo.parking;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.time.Clock;


@Configuration
class ParkingConfiguration {

    @Bean
    public Clock getClock() {
        return Clock.systemUTC();
    }

    @Primary
    @Bean
    public CarRepository getCarRepository(Clock clock, DataSource dataSource) {


        return new PostgresCarRepositoryImpl(clock, dataSource);
    }


}
