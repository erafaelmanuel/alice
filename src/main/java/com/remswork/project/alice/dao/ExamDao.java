package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Exam;

import java.util.List;

public interface ExamDao {

    Exam getExamById(long id) throws GradingFactorException;

    List<Exam> getExamList() throws GradingFactorException;

    List<Exam> getExamListByStudentAndSubjectId(long studentId, long subjectId) throws GradingFactorException;

    List<Exam> getExamListByStudentAndSubjectId(long studentId, long subjectId, long termId)
            throws GradingFactorException;

    Exam addExam(Exam exam, long studentId, long subjectId) throws GradingFactorException;

    Exam addExam(Exam exam, long studentId, long subjectId, long termId) throws GradingFactorException;

    Exam updateExamById(long id, Exam newExam, long studentId, long subjectId) throws GradingFactorException;

    Exam updateExamById(long id, Exam newExam, long studentId, long subjectId, long termId)
            throws GradingFactorException;

    Exam deleteExamById(long id) throws GradingFactorException;
}
