package com.aj.parkingdemo.parking;

import com.aj.parkingdemo.parking.dto.CountryCode;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.time.Clock;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class PostgresCarRepositoryImplTest {

    private static DataSource dataSource;
    private static final String CREATE_SQL = "create table parking (\n" +
            "plate_number varchar(100), \n" +
            "country_code char(2),\n" +
            "entry_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
            "weight SMALLINT,\n" +
            "CONSTRAINT parking_pk\n" +
            "   PRIMARY KEY (plate_number, country_code)\n" +
            ");";
    private PostgresCarRepositoryImpl postgresCarRepositoryimpl;

    @BeforeClass
    public static void beforeClass() throws Exception {
        dataSource = getDataSource();
        dataSource.getConnection().createStatement().execute(CREATE_SQL);
        dataSource.getConnection().createStatement().execute("insert into parking(plate_number, country_code) values ('KBO8760', 'PL');");
        dataSource.getConnection().createStatement().execute("insert into parking(plate_number, country_code) values ('T0D3L3T', 'PL');");
    }

    @Before
    public void setUp() {

        postgresCarRepositoryimpl = new PostgresCarRepositoryImpl(Clock.systemUTC(),dataSource);
    }
    @Test
    public void createNewCar() {
        Car car = postgresCarRepositoryimpl.checkIn(CountryCode.PL, "KBO8722",560);
        Assert.assertEquals(CountryCode.PL, car.getCountryOfOrigin());
        assertEquals("KBO8722", car.getLicensePlate());
        assertEquals(560, car.getWeightInKgs());
    }
    @Test
    public void removeCar() {
     postgresCarRepositoryimpl.checkOut(CountryCode.PL, "T0D3L3T");
    }
    @Test(expected = CarNotFoundException.class)
    public void removeCarWhichDoesntExist() {
        postgresCarRepositoryimpl.checkOut(CountryCode.PL, "lipa");
    }
    @Test(expected = ParkingDBException.class)
    public void removeCarWhithFailingDBExpection() {
        PostgresCarRepositoryImpl bogus = new PostgresCarRepositoryImpl(Clock.systemUTC(), new FaultyDataSource());
         bogus.checkOut(CountryCode.PL, "KBO8761");
    }
    @Test(expected = ParkingDBException.class)
    public void createCarWhithFailingDBExpection() {
        PostgresCarRepositoryImpl bogus = new PostgresCarRepositoryImpl(Clock.systemUTC(), new FaultyDataSource());
        bogus.checkIn(CountryCode.PL, "KBO8761",550);
    }

    @Test
    public void getCar() {
        Car car = postgresCarRepositoryimpl.getCar(CountryCode.PL, "KBO8760");
        assertNotNull(car);

    }
    @Test(expected = CarNotFoundException.class)
    public void getCarNotFound() {
        postgresCarRepositoryimpl.getCar(CountryCode.PL, "KBO8761");
    }

    @Test(expected = ParkingDBException.class)
    public void getCarSqlException() {
        PostgresCarRepositoryImpl bogus = new PostgresCarRepositoryImpl(Clock.systemUTC(), new FaultyDataSource());
        bogus.getCar(CountryCode.PL, "KBO8761");
    }
    private static DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:generated-keys");
        config.setUsername("sa");
        config.setPassword("");
        return new HikariDataSource(config);
    }

    private static class FaultyDataSource implements DataSource {
        @Override
        public Connection getConnection() throws SQLException {
            throw new SQLException();
        }

        @Override
        public Connection getConnection(String s, String s1) {
            return null;
        }

        @Override
        public PrintWriter getLogWriter() {
            return null;
        }

        @Override
        public void setLogWriter(PrintWriter printWriter) {

        }

        @Override
        public void setLoginTimeout(int i) {

        }

        @Override
        public int getLoginTimeout() {
            return 0;
        }

        @Override
        public <T> T unwrap(Class<T> aClass) {
            return null;
        }

        @Override
        public boolean isWrapperFor(Class<?> aClass) {
            return false;
        }

        @Override
        public Logger getParentLogger() {
            return null;
        }
    }
}