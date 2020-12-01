package com.pixeon.healthcare.usecases.deleteexam;

import com.pixeon.healthcare.domain.exception.InstitutionDoesNotOwnExamException;
import com.pixeon.healthcare.domain.exception.InstitutionNotFoundException;
import com.pixeon.healthcare.domain.models.ExamModel;
import com.pixeon.healthcare.domain.models.HealthcareInstitutionDTO;
import com.pixeon.healthcare.usecases.createexam.ExamService;
import com.pixeon.healthcare.usecases.createhealthcareInstitution.HealthcareInstitutionFactory;
import com.pixeon.healthcare.usecases.deleteexam.exception.ExamNotFoundException;

public class DeleteExam {

    private ExamService examService;
    private HealthcareInstitutionFactory institutionService;

    public DeleteExam(HealthcareInstitutionFactory institutionService, ExamService examService) {
        this.examService = examService;
        this.institutionService = institutionService;
    }

    public boolean delete(int examId) {
        ExamModel exam = getExam(examId);
        HealthcareInstitutionDTO examInstitution = getInstitutionOfExam(exam);
        checkIfInstitutionOwnsExam(examInstitution);
        return examService.delete(exam);
    }

    private HealthcareInstitutionDTO getInstitutionOfExam(ExamModel examModel) {
        HealthcareInstitutionDTO examInstitution = institutionService.getInstitutionForExamBy(examModel.getId());
        if (examInstitution == null) {
            throw new InstitutionNotFoundException();
        }
        return examInstitution;
    }

    private void checkIfInstitutionOwnsExam(HealthcareInstitutionDTO examInstitution) {
        HealthcareInstitutionDTO currentInstitution = institutionService.getCurrentInstitution();
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
