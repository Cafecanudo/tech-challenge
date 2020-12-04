package com.pixeon.healthcare.instituitionservice.config.exception;

import com.pixeon.healthcare.domain.config.exception.*;
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
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse conflit(Exception except) {
        return ApiErrorResponse.builder()
                .message(except.getMessage())
                .cause(except.getClass().getSimpleName())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {NoBalanceToCreateExamException.class})
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    public ApiErrorResponse noModified(Exception except) {
        return ApiErrorResponse.builder()
                .message(except.getMessage())
                .cause(except.getClass().getSimpleName())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {NegativeValuesNotAcceptedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse notAccepted(Exception except) {
        return ApiErrorResponse.builder()
                .message(except.getMessage())
                .cause(except.getClass().getSimpleName())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {InstitutionNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse notFound(Exception except) {
        return ApiErrorResponse.builder()
                .message(except.getMessage())
                .cause(except.getClass().getSimpleName())
                .build();
    }
}
