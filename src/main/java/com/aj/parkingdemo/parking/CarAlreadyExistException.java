package com.aj.parkingdemo.parking;

class CarAlreadyExistException extends RuntimeException {
    public CarAlreadyExistException() {
    }

    public CarAlreadyExistException(String message) {
        super(message);
    }

    public CarAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public CarAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

