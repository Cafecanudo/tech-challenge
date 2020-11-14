package com.pixeon.healthcare.usecases.deleteexam;

import com.pixeon.healthcare.domain.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.exception.InstitutionNotFoundException;
import com.pixeon.healthcare.domain.models.ExamModel;
import com.pixeon.healthcare.domain.models.HealthcareInstitution;
import com.pixeon.healthcare.usecases.createexam.ExamService;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.HealthcareInstitutionService;
import com.pixeon.healthcare.usecases.deleteexam.exception.ExamNotFoundException;

public class DeleteExam {

    private ExamService examService;
    private HealthcareInstitutionService institutionService;

    public DeleteExam(HealthcareInstitutionService institutionService, ExamService examService) {
        this.examService = examService;
        this.institutionService = institutionService;
    }

    public boolean delete(int examId) {
        ExamModel exam = getExam(examId);
        HealthcareInstitution examInstitution = getInstitutionOfExam(exam);
        checkIfInstitutionOwnsExam(examInstitution);
        return examService.delete(exam);
    }

    private HealthcareInstitution getInstitutionOfExam(ExamModel examModel) {
        HealthcareInstitution examInstitution = institutionService.getInstitutionForExamBy(examModel.getId());
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

    private ExamModel getExam(int examId) {
        ExamModel examModel = this.examService.getExameById(examId);
        if (examModel == null) {
            throw new ExamNotFoundException();
        }
        return examModel;
    }
}
