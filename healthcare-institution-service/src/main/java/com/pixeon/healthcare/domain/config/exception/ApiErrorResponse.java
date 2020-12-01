package com.pixeon.healthcare.domain.config.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated
public class ApiErrorResponse {

    private Boolean externalService;
    private HttpStatus status;
    private int code;
    private String message;
    private String cause;

}
