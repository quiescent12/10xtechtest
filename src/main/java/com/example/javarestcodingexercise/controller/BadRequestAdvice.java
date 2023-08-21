package com.example.javarestcodingexercise.controller;

import com.example.javarestcodingexercise.exception.InsufficientFundsException;
import com.example.javarestcodingexercise.exception.TargetAccountIsSourceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class BadRequestAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { InsufficientFundsException.class, TargetAccountIsSourceException.class })
    protected ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
