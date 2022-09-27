package com.aj.parkingdemo.parking;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "db")
@Data
class DBProperties {

    private String jdbcUrl;
    private String username;
    private String password;
}