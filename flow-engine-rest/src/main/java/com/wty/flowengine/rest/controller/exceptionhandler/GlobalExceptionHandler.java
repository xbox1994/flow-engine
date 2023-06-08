package com.wty.flowengine.rest.controller.exceptionhandler;

import com.wty.flowengine.api.response.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final int CODE_FAILURE = -1;

    @ExceptionHandler
    public BaseResponse<Object> handleException(Exception e) {
        return BaseResponse.fail(CODE_FAILURE, e.getMessage());
    }
}
