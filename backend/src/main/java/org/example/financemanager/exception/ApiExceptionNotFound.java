package org.example.financemanager.exception;

public class ApiExceptionNotFound extends RuntimeException{
    public ApiExceptionNotFound(String message) {
        super(message);
    }
}
