package com.pixeon.healthcare.domain.usecases.updateexam.impl;

import com.pixeon.healthcare.domain.config.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.domain.config.exception.IdCantNullException;
import com.pixeon.healthcare.domain.config.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.config.exception.InstitutionNotFoundException;
import com.pixeon.healthcare.domain.entities.Exam;
import com.pixeon.healthcare.domain.entities.HealthcareInstitution;
import com.pixeon.healthcare.domain.usecases.createexam.ExamGateway;
import com.pixeon.healthcare.domain.usecases.createhealthcareinstitution.HealthcareInstitutionGateway;
import com.pixeon.healthcare.domain.usecases.updateexam.UpdateExam;

public class UpdateExamImpl implements UpdateExam {

    private ExamGateway examGateway;
    private HealthcareInstitutionGateway institutionService;

    public UpdateExamImpl(HealthcareInstitutionGateway institutionService, ExamGateway examGateway) {
        this.institutionService = institutionService;
        this.examGateway = examGateway;
    }

    public Exam update(Exam exam) {
        validFields(exam);
        HealthcareInstitution examInstitution = getInstitutionOfExam(exam);
        checkIfInstitutionOwnsExam(examInstitution);
        return examGateway.update(exam);
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

    private void validFields(Exam exam) {
        if (exam.isNullId()) {
            throw new IdCantNullException();
        }
        if (exam.isEmptyFields()) {
            throw new CreateExamFieldEmptyException();
        }
    }

}
