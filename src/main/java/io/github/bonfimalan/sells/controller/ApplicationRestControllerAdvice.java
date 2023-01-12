package io.github.bonfimalan.sells.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.bonfimalan.sells.exception.ServiceException;
import io.github.bonfimalan.sells.rest.ApiErrors;

@RestControllerAdvice
public class ApplicationRestControllerAdvice {
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleServiceException(ServiceException exception) {
        String message = exception.getMessage();
        return new ApiErrors(message);
    }
}
