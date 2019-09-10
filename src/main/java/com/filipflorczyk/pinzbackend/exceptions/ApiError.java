package com.filipflorczyk.pinzbackend.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Getter
public class ApiError {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message.replace("java.lang.", "");
        errors.forEach(s -> s.replace("java.lang.", ""));
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message.replace("java.lang.", "");
        errors = Arrays.asList(error.replace("java.lang.", ""));
    }
}
