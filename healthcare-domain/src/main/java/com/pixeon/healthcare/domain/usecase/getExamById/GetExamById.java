package com.pixeon.healthcare.domain.usecase.getExamById;

import com.pixeon.healthcare.domain.entity.Exam;

@FunctionalInterface
public interface GetExamById {

    Exam get(int examId);
}
