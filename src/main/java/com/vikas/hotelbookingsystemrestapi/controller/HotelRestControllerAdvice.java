package com.vikas.hotelbookingsystemrestapi.controller;

import com.vikas.hotelbookingsystemrestapi.exceptionHandling.CustomError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class HotelRestControllerAdvice extends ResponseEntityExceptionHandler {

    private Map<String, String> messages = new HashMap<>();

    @ExceptionHandler(value = {IOException.class})
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>("Bad request made", HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = {NullPointerException.class, NumberFormatException.class})
    protected ResponseEntity<Object> handleNotFoundRequest(RuntimeException ex, WebRequest request) {
        Map<String, String> maps = new HashMap<>();
        maps.put("Message: ", "Not found");
        maps.put("Description", "No such record found, make sure you pass the value.");
        return new ResponseEntity<>(maps, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(value = {CustomError.class})
    protected ResponseEntity<Object> customException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex, WebRequest request) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        messages.clear();
        for (ConstraintViolation<?> violations: constraintViolations) {
            messages.put(String.valueOf(violations.getPropertyPath()), violations.getMessage());
        }
        return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value =  {AuthenticationException.class})
    protected ResponseEntity<Boolean> authenticationException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }

//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    protected ResponseEntity<String> missingReqParameterException(MissingServletRequestParameterException ex, WebRequest request) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
