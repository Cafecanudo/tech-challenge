package com.pixeon.healthcare.domain.usecase.updateexam;

import com.pixeon.healthcare.domain.model.ExamModel;

@FunctionalInterface
public interface UpdateExam {

    ExamModel update(ExamModel examModel);
}
