package com.pixeon.healthcare.domain.usecase.getExamById;

import com.pixeon.healthcare.domain.model.ExamModel;

@FunctionalInterface
public interface GetExamById {

    ExamModel get(int examId);
}
