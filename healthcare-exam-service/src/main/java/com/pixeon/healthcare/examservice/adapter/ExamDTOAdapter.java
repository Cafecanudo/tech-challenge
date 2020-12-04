package com.pixeon.healthcare.examservice.adapter;

import com.pixeon.healthcare.domain.model.ExamModel;
import com.pixeon.healthcare.domain.model.enums.GenderEnum;
import com.pixeon.healthcare.examservice.entrypoint.rest.createexam.ExamDTO;

public class ExamDTOAdapter {

    public static ExamModel toModel(ExamDTO examDTO) {
        return ExamModel.builder()
                .id(examDTO.getId())
                .patientName(examDTO.getPatientName())
                .patientAge(examDTO.getPatientAge())
                .patientGender(GenderEnum.valueOf(examDTO.getPatientGender()))
                .physicianName(examDTO.getPhysicianName())
                .physicianCRM(examDTO.getPhysicianCRM())
                .procedureName(examDTO.getProcedureName())
                .billed(examDTO.isBilled())
                .build();
    }
}
