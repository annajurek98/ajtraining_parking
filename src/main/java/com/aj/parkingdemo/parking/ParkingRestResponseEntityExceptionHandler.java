
package com.aj.parkingdemo.parking;

import com.aj.parkingdemo.parking.dto.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class ParkingRestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {CarNotFoundException.class})
    protected ResponseEntity<Object> handleCarNotFound(
            RuntimeException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO("Resource doesn't exist");
        return handleExceptionInternal(ex, errorDTO,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value
            = {CarAlreadyExistException.class})
    protected ResponseEntity<Object> handleCarAlreadyExist(
            RuntimeException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO("Resource duplicated");
        return handleExceptionInternal(ex, errorDTO,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}