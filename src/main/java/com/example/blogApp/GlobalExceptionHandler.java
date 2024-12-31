package com.example.blogApp;

import com.amazonaws.AmazonClientException;
import com.example.blogApp.exceptions.ResourceNotFoundException;
import com.example.blogApp.payloads.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFound(ResourceNotFoundException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> invalidMethod(MethodArgumentNotValidException ex) {

        Map<String, Object> map = new HashMap<>();
        List<String> errorMessage = ex.getBindingResult().getAllErrors().stream().map((err) -> err.getDefaultMessage()).toList();

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {


        List<String> errorMessage = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>("Runtime Exception: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<String> handleAwsException(AmazonClientException ex) {
        return new ResponseEntity<>("Runtime Exception: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse> handleAuthException(AuthenticationException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse> handleMissingParams(MissingServletRequestParameterException ex) {


        String paramName = ex.getParameterName(); // The name of the missing parameter
        String errorMessage = "Required request parameter '" + paramName + "' is missing.";
        ApiResponse apiResponse = new ApiResponse(errorMessage, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }


}
