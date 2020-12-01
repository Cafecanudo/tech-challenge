package com.pixeon.healthcare.domain.usecases.updateexam;

import com.pixeon.healthcare.domain.entities.Exam;

@FunctionalInterface
public interface UpdateExam {

    Exam update(Exam exam);
}
