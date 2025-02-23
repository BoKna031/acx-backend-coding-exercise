package com.accelex.sample.exercise.handler;

import com.accelex.sample.exercise.exception.ErrorDetail;
import com.accelex.sample.exercise.exception.EntityConflictException;
import com.accelex.sample.exercise.exception.ElementNotFoundException;
import com.accelex.sample.exercise.exception.RentalNotPossibleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleElementNotFoundException(ElementNotFoundException enf){
        return new ResponseEntity<>(
                ErrorDetail.builder()
                        .title("Element does not exist")
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(enf.getMessage())
                        .build(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RentalNotPossibleException.class)
    public ResponseEntity<ErrorDetail> handleRentalNotPossible(RentalNotPossibleException rnp){
        return new ResponseEntity<>(
            ErrorDetail.builder()
                .title("Not possible to rent vehicle")
                .status(HttpStatus.BAD_REQUEST.value())
                .message(rnp.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityConflictException.class)
    public ResponseEntity<ErrorDetail> handleEntityConflictException(EntityConflictException cce){
        return new ResponseEntity<>(
                ErrorDetail.builder()
                        .title("Entity can't be created")
                        .status(HttpStatus.CONFLICT.value())
                        .message(cce.getMessage())
                        .build(), HttpStatus.CONFLICT);
    }
}
