package com.pixeon.healthcare.domain.usecases.createexam;

import com.pixeon.healthcare.domain.entities.Exam;

public interface ExamGateway {

    Exam save(Exam exam);

    Exam update(Exam exam);

    boolean delete(Exam exam);

    Exam getExameById(int examId);
}
