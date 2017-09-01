package com.remswork.project.alice.service;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Exam;

import java.util.List;

public interface QuizService {

    Exam getExamById(long id) throws GradingFactorException;

    List<Exam> getExamList() throws GradingFactorException;

    List<Exam> getExamListByStudentAndSubjectId(long studentId, long subjectId) throws GradingFactorException;

    Exam addExam(Exam quiz, long studentId, long subjectId) throws GradingFactorException;

    Exam updateExamById(long id, Exam newExam, long studentId, long subjectId) throws GradingFactorException;

    Exam deleteExamById(long id) throws GradingFactorException;
}
