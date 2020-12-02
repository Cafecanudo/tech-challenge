package com.pixeon.healthcare.instituitionservice.config.exception;

import com.pixeon.healthcare.domain.config.exception.CNPJEmptyException;
import com.pixeon.healthcare.domain.config.exception.CNPJInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse internalErrorException(Exception except) {
        return ApiErrorResponse.builder()
                .message(except.getMessage())
                .cause(except.getClass().getSimpleName())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {CNPJInvalidException.class, CNPJEmptyException.class, AlreadyExistsCNPJException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse badRequest(Exception except) {
        return ApiErrorResponse.builder()
                .message(except.getMessage())
                .cause(except.getClass().getSimpleName())
                .build();
    }

}
