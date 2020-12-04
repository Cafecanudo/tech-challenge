package com.pixeon.healthcare.examservice.adapter;

import com.pixeon.healthcare.domain.model.ExamModel;
import com.pixeon.healthcare.domain.model.HealthcareInstitutionModel;
import com.pixeon.healthcare.domain.model.enums.GenderEnum;
import com.pixeon.healthcare.examservice.config.entity.Exam;

public class ExamEntityAdapter {

    public static ExamModel toModel(Exam exam) {
        return ExamModel.builder()
                .id(exam.getId())
                .healthcareInstitution(HealthcareInstitutionModel.builder().id(exam.getHealthcareInstitutionId()).build())
                .patientName(exam.getPatientName())
                .patientAge(exam.getPatientAge())
                .patientGender(GenderEnum.valueOf(exam.getPatientGender().name()))
                .physicianName(exam.getPhysicianName())
                .physicianCRM(exam.getPhysicianCRM())
                .procedureName(exam.getProcedureName())
                .billed(exam.isBilled())
                .build();

    }
}
