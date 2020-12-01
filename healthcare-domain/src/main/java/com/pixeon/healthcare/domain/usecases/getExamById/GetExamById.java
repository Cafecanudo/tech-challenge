package com.pixeon.healthcare.domain.usecases.getExamById;

import com.pixeon.healthcare.domain.entities.Exam;

@FunctionalInterface
public interface GetExamById {

    Exam get(int examId);
}
