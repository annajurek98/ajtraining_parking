package com.aj.parkingdemo.parking;

import com.aj.parkingdemo.parking.dto.CountryCode;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Clock;
import java.time.Instant;

@Component
@RequiredArgsConstructor
class PostgresCarRepositoryImpl implements CarRepository {

    public static final String SELECT = "select plate_number,country_code " +
            "FROM parking " +
            "WHERE plate_number = ? " +
            "AND country_code = ?";
    private static final String INSERT_CAR = "insert into parking(plate_number, country_code, weight) " +
            " values (?, ?, ?)";
    private static final String DELETE = "delete from parking where plate_number = ? and country_code = ?";
    private final Clock clock;
    private final DataSource data;


    @Override
    public Car checkIn(CountryCode countryCode, String licensePlate, int weightInKgs) {
        try (Connection connection = data.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CAR, new String[]{"entry_time"})) {
            preparedStatement.setString(1, licensePlate);
            preparedStatement.setString(2, countryCode.name());
            preparedStatement.setInt(3, weightInKgs);
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeysResultSet = preparedStatement.getGeneratedKeys()) {
                if (generatedKeysResultSet.next()) {
                    var entryTime = generatedKeysResultSet.getTimestamp("entry_time");
                    return new Car(countryCode, licensePlate, entryTime.toInstant(), weightInKgs);
                }
                throw new ParkingDBException();
            }
        } catch (SQLException e) {
            if (e instanceof PSQLException) {
                if ("parking_pk".equals(((PSQLException) e).getServerErrorMessage().getConstraint())) {
                    throw new CarAlreadyExistException(e);
                }
            }
            throw new ParkingDBException(e);
        }
    }

    @Override
    public Car getCar(CountryCode countryCode, String licensePlate) {
        try (Connection connection = data.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(SELECT)) {
            preparedStatement.setString(1, licensePlate);
            preparedStatement.setString(2, countryCode.name());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    CountryCode countryCode1 = CountryCode.fromString(rs.getString(2));
                    String plate = rs.getString(1);
                    return new Car(countryCode1, plate, Instant.now(), 450);
                }
            }
        } catch (SQLException ex) {
            throw new ParkingDBException(ex);
        }
        throw new CarNotFoundException();

    }

    @Override
    public void checkOut(CountryCode countryCode, String licensePlate) {
        try (Connection connection = data.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setString(1, licensePlate);
            preparedStatement.setString(2, countryCode.name());
            int changeRows = preparedStatement.executeUpdate();
            if (changeRows == 0) {
                throw new CarNotFoundException();
            }
        } catch (SQLException e) {
            throw new ParkingDBException(e);
        }
    }
}
