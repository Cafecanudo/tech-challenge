package com.pixeon.healthcare.applicationservice.entrypoint.rest.getvalueforcreateexam;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ValueForCreateExamDTO {

    private BigDecimal value;
}
