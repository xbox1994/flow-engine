package com.wty.flowengine.converter.exception;

public class ConvertException extends RuntimeException{
    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(String message, Throwable cause) {
        super(message, cause);
    }
}
