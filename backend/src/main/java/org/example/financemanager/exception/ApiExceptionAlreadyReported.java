package org.example.financemanager.exception;

public class ApiExceptionAlreadyReported extends RuntimeException{
    public ApiExceptionAlreadyReported(String message) {
        super(message);
    }
}
