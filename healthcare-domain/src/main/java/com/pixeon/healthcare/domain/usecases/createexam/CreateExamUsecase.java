package com.pixeon.healthcare.domain.usecases.createexam;

import com.pixeon.healthcare.domain.entities.Exam;

@FunctionalInterface
public interface CreateExamUsecase {

    Exam create(Exam exam);

}
