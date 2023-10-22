package com.accelex.sample.exercise.exception;

public class CustomerConflictException extends RuntimeException{

    public CustomerConflictException(String message){
        super(message);
    }
}
