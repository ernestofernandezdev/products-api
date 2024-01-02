package com.ferdev.restful.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ProductErrorResponse> notFoundError(ProductNotFoundException exc) {
        ProductErrorResponse error = new ProductErrorResponse();

        error.setMessage(exc.getClass().getName() + ": " + exc.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ProductErrorResponse> invalidQueryParam(InvalidQueryParamException exc) {
        ProductErrorResponse error = new ProductErrorResponse();

        error.setMessage(exc.getClass().getName() + ": " + exc.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ProductErrorResponse> invalidData(MethodArgumentNotValidException exc) {
        ProductErrorResponse error = new ProductErrorResponse();

        error.setMessage(exc.getClass().getName() + ": Some of the fields are not valid for the request.");
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ProductErrorResponse> httpRequestBodyTypeMismatch(HttpMessageNotReadableException exc) {
        ProductErrorResponse error = new ProductErrorResponse();

        error.setMessage(exc.getClass().getName() + ": The type of some of the fields is invalid");
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ProductErrorResponse> generalError(RuntimeException exc) {
        ProductErrorResponse error = new ProductErrorResponse();

        error.setMessage("Unexpected failure occurred: " + exc.fillInStackTrace().getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
