package com.aj.parkingdemo.parking;
class ParkingDBException extends RuntimeException {
    public ParkingDBException() {
    }

    public ParkingDBException(String message) {
        super(message);
    }

    public ParkingDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParkingDBException(Throwable cause) {
        super(cause);
    }

    public ParkingDBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
