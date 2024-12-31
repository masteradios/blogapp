package com.example.blogApp.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidJsonException extends AuthenticationException {

    public InvalidJsonException(String message) {
        super(message);
    }
}
