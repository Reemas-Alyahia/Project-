package com.example.projec3.ApiResponse;

public class ApiException extends RuntimeException {
    public ApiException (String message) {
        super(message);
    }
}