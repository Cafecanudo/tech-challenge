package com.pixeon.healthcare.domain.usecase.createexam;

import com.pixeon.healthcare.domain.model.ExamModel;

@FunctionalInterface
public interface CreateExamUsecase {

    ExamModel create(ExamModel examModel);

}
