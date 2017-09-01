package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Exam;
import com.remswork.project.alice.model.Quiz;

import java.util.List;

public interface QuizDao {

    Quiz getQuizById(long id) throws GradingFactorException;

    List<Quiz> getQuizList() throws GradingFactorException;

    List<Quiz> getQuizListByStudentAndSubjectId(long studentId, long subjectId) throws GradingFactorException;

    Quiz addQuiz(Quiz quiz, long studentId, long subjectId) throws GradingFactorException;

    Quiz updateQuizById(long id, Quiz newQuiz, long studentId, long subjectId) throws GradingFactorException;

    Quiz deleteQuizById(long id) throws GradingFactorException;
}
