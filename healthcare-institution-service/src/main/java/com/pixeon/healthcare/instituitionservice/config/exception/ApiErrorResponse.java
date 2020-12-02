package com.pixeon.healthcare.instituitionservice.config.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated
public class ApiErrorResponse {

    private String message;
    private String cause;

}
