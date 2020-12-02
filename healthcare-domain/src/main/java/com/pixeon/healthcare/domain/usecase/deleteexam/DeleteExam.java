package com.pixeon.healthcare.domain.usecase.deleteexam;

@FunctionalInterface
public interface DeleteExam {

    boolean delete(int examId);
}
