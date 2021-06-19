package com.vikas.hotelbookingsystemrestapi.exceptionHandling;

public class CustomError extends RuntimeException{

    public CustomError(String msg) {
        super(msg);
    }
}
