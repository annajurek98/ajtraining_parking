package com.aj.parkingdemo.parking;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@EnableConfigurationProperties(DBProperties.class)
@Configuration
class DataSourceConfig {

    @Bean
    public DataSource getDataSource(DBProperties dbProperties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbProperties.getJdbcUrl());
        config.setUsername(dbProperties.getUsername());
        config.setPassword(dbProperties.getPassword());
        return new HikariDataSource(config);
    }
}