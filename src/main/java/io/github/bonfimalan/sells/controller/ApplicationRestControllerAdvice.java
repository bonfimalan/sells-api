package io.github.bonfimalan.sells.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.bonfimalan.sells.exception.ApiErrors;
import io.github.bonfimalan.sells.exception.NotFoundException;
import io.github.bonfimalan.sells.exception.ServiceException;

@RestControllerAdvice
public class ApplicationRestControllerAdvice {
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBadRequest(ServiceException exception) {
        String message = exception.getMessage();
        return new ApiErrors(message);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleNotFound(NotFoundException exception) {
        String message = exception.getMessage();
        return new ApiErrors(message);
    }
}
