package com.ferdev.restful.api.exceptions;

public class InvalidQueryParamException extends RuntimeException{
    public InvalidQueryParamException(String message) {
        super(message);
    }

    public InvalidQueryParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidQueryParamException(Throwable cause) {
        super(cause);
    }
}
