package com.accelex.sample.exercise.exception;

public class EntityConflictException extends RuntimeException{

    public EntityConflictException(String message){
        super(message);
    }
}
