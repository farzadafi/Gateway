package com.example.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "send true method")
public class MethodNotAllowedException extends RuntimeException{

    public MethodNotAllowedException(String message) {
        super(message);
    }
}
