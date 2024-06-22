package org.example.financemanager.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.example.financemanager.dto.ErrorResponse;

@RestControllerAdvice(basePackages = "org.example.financemanager.controller")
public class HandlerExceptions {
    @ExceptionHandler(value = {ApiExceptionNotFound.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorResponse handleApiRequestExceptionNotFound(@NonNull HttpServletRequest request, ApiExceptionNotFound exception){
        return new ErrorResponse(request.getRequestURI(), exception.getMessage());
    }

    @ExceptionHandler(value = {ApiExceptionAlreadyReported.class})
    @ResponseStatus(code = HttpStatus.ALREADY_REPORTED)
    public ErrorResponse handleApiRequestExceptionAlreadyReported(@NonNull HttpServletRequest request, ApiExceptionAlreadyReported exception){
        return new ErrorResponse(request.getRequestURI(), exception.getMessage());
    }

    @ExceptionHandler(value = {NotVerifiedEmailException.class})
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ErrorResponse handleNotVerifiedEmailException(@NonNull HttpServletRequest request, NotVerifiedEmailException exception){
        return new ErrorResponse(request.getRequestURI(), exception.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(@NonNull HttpServletRequest request, MethodArgumentNotValidException exception){
        final String[] errorMessage = new String[1];
        exception.getBindingResult().getAllErrors().forEach((error) -> errorMessage[0] = error.getDefaultMessage());
        return new ErrorResponse(request.getRequestURI(), errorMessage[0]);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ErrorResponse handleBadCredentialsException(@NonNull HttpServletRequest request, BadCredentialsException exception){
        return new ErrorResponse(request.getRequestURI(), exception.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(@NonNull HttpServletRequest request, Exception exception){
        return new ErrorResponse(request.getRequestURI(), exception.getMessage());
    }
}
