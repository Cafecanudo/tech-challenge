package com.pixeon.healthcare.domain.usecase.deleteexam.impl;

import com.pixeon.healthcare.domain.config.exception.ExamNotFoundException;
import com.pixeon.healthcare.domain.config.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.config.exception.InstitutionNotFoundException;
import com.pixeon.healthcare.domain.entity.Exam;
import com.pixeon.healthcare.domain.entity.HealthcareInstitution;
import com.pixeon.healthcare.domain.usecase.createexam.ExamGateway;
import com.pixeon.healthcare.domain.usecase.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecase.deleteexam.DeleteExam;

public class DeleteExamImpl implements DeleteExam {

    private ExamGateway examGateway;
    private HealthcareInstitutionGateway institutionService;

    public DeleteExamImpl(HealthcareInstitutionGateway institutionService, ExamGateway examGateway) {
        this.examGateway = examGateway;
        this.institutionService = institutionService;
    }

    public boolean delete(int examId) {
        Exam exam = getExam(examId);
        HealthcareInstitution examInstitution = getInstitutionOfExam(exam);
        checkIfInstitutionOwnsExam(examInstitution);
        return examGateway.delete(exam);
    }

    private HealthcareInstitution getInstitutionOfExam(Exam exam) {
        HealthcareInstitution examInstitution = institutionService.getInstitutionForExamBy(exam.getId());
        if (examInstitution == null) {
            throw new InstitutionNotFoundException();
        }
        return examInstitution;
    }

    private void checkIfInstitutionOwnsExam(HealthcareInstitution examInstitution) {
        HealthcareInstitution currentInstitution = institutionService.getCurrentInstitution();
        if (!currentInstitution.equals(examInstitution)) {
            throw new InstitutionDoesNotOwnExamException();
        }
    }

    private Exam getExam(int examId) {
        Exam exam = this.examGateway.getExameById(examId);
        if (exam == null) {
            throw new ExamNotFoundException();
        }
        return exam;
    }

}
