package com.accelex.sample.exercise.exception;

public class ElementNotFoundException extends RentalNotPossibleException {

    public ElementNotFoundException(String message) {
        super(message);
    }

    public ElementNotFoundException(String elementType, String id) {
        super(elementType + " with id = " + id + " doesn't exists");
    }
}
