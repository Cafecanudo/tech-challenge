package com.pixeon.healthcare.applicationservice.entrypoint.rest.getvalueforconsultingexam;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ValueForConsultingExamDTO {

    private BigDecimal value;
}
