package com.pixeon.healthcare.domain.usecase.createexam;

import com.pixeon.healthcare.domain.entity.Exam;

public interface ExamGateway {

    Exam save(Exam exam);

    Exam update(Exam exam);

    boolean delete(Exam exam);

    Exam getExameById(int examId);
}
