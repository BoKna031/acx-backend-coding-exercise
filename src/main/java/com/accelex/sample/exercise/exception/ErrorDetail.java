package com.accelex.sample.exercise.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetail {

    private String title;
    private int status;
    private String message;
}
