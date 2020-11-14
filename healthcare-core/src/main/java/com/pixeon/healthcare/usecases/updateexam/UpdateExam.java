package com.pixeon.healthcare.usecases.updateexam;

import com.pixeon.healthcare.models.ExamModel;
import com.pixeon.healthcare.usecases.createexam.ExamService;
import com.pixeon.healthcare.usecases.createexam.exception.CreateExamFieldEmptyException;
import com.pixeon.healthcare.usecases.updateexam.exception.IdCantNullException;

public class UpdateExam {

    private ExamService examService;

    public UpdateExam(ExamService examService) {
        this.examService = examService;
    }

    public ExamModel update(ExamModel examModel) {
        valid(examModel);
        return examService.update(examModel);
    }

    private void valid(ExamModel examModel) {
        if (examModel.isNullId()){
            throw new IdCantNullException();
        }
        if (examModel.isEmptyFields()){
            throw new CreateExamFieldEmptyException();
        }
    }
}
