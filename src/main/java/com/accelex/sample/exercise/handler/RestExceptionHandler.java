package com.accelex.sample.exercise.handler;

import com.accelex.sample.exercise.exception.BadRequestDetail;
import com.accelex.sample.exercise.exception.RentalNotPossibleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RentalNotPossibleException.class)
    public ResponseEntity<BadRequestDetail> handleRentalNotPossible(RentalNotPossibleException rnp){
        return new ResponseEntity<>(
            BadRequestDetail.builder()
                .title("Not possible to rent vehicle")
                .status(HttpStatus.BAD_REQUEST.value())
                .message(rnp.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
