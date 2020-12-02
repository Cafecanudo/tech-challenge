package com.pixeon.healthcare.domain.usecase.updateexam;

import com.pixeon.healthcare.domain.entity.Exam;

@FunctionalInterface
public interface UpdateExam {

    Exam update(Exam exam);
}
