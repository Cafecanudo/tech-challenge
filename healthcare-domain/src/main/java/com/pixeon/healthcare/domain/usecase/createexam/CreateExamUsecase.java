package com.pixeon.healthcare.domain.usecase.createexam;

import com.pixeon.healthcare.domain.entity.Exam;

@FunctionalInterface
public interface CreateExamUsecase {

    Exam create(Exam exam);

}
