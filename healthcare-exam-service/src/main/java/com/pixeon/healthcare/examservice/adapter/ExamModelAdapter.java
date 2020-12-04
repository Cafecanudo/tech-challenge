package com.pixeon.healthcare.examservice.adapter;

import com.pixeon.healthcare.domain.model.ExamModel;
import com.pixeon.healthcare.examservice.config.entity.Exam;
import com.pixeon.healthcare.examservice.config.entity.enums.GenderEnum;
import com.pixeon.healthcare.examservice.entrypoint.rest.createexam.ExamDTO;

public class ExamModelAdapter {

    public static ExamDTO toDTO(ExamModel exam) {
        return ExamDTO.builder()
                .id(exam.getId())
                .healthcareInstitutionId(exam.getHealthcareInstitution().getId())
                .patientName(exam.getPatientName())
                .patientAge(exam.getPatientAge())
                .patientGender(exam.getPatientGender().name())
                .physicianName(exam.getPhysicianName())
                .physicianCRM(exam.getPhysicianCRM())
                .procedureName(exam.getProcedureName())
                .billed(exam.isBilled())
                .build();
    }

    public static Exam toEntity(ExamModel examModel) {
        return Exam.builder()
                .id(examModel.getId())
                .healthcareInstitutionId(examModel.getHealthcareInstitution().getId())
                .patientName(examModel.getPatientName())
                .patientAge(examModel.getPatientAge())
                .patientGender(GenderEnum.valueOf(examModel.getPatientGender().name()))
                .physicianName(examModel.getPhysicianName())
                .physicianCRM(examModel.getPhysicianCRM())
                .procedureName(examModel.getProcedureName())
                .billed(examModel.isBilled())
                .build();
    }
}
