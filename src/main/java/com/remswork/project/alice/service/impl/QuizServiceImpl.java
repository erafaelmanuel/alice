package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.QuizDaoImpl;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Quiz;
import com.remswork.project.alice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizDaoImpl quizDao;


    @Override
    public Quiz getQuizById(long id) throws GradingFactorException {
        return quizDao.getQuizById(id);
    }

    @Override
    public List<Quiz> getQuizList() throws GradingFactorException {
        return quizDao.getQuizList();
    }

    @Override
    public List<Quiz> getQuizListByStudentAndSubjectId(long studentId, long subjectId)
            throws GradingFactorException {
        return quizDao.getQuizListByStudentAndSubjectId(studentId, subjectId);
    }

    @Override
    public List<Quiz> getQuizListByStudentAndSubjectId(long studentId, long subjectId, long termId)
            throws GradingFactorException {
        return quizDao.getQuizListByStudentAndSubjectId(studentId, subjectId, termId);
    }

    @Override
    public Quiz addQuiz(Quiz quiz, long studentId, long subjectId) throws GradingFactorException {
        return quizDao.addQuiz(quiz, studentId, subjectId);
    }

    @Override
    public Quiz addQuiz(Quiz quiz, long studentId, long subjectId, long termId) throws GradingFactorException {
        return quizDao.addQuiz(quiz, studentId, subjectId, termId);
    }

    @Override
    public Quiz updateQuizById(long id, Quiz newQuiz, long studentId, long subjectId)
            throws GradingFactorException {
        return quizDao.updateQuizById(id, newQuiz, studentId, subjectId);
    }

    @Override
    public Quiz updateQuizById(long id, Quiz newQuiz, long studentId, long subjectId, long termId)
            throws GradingFactorException {
        return quizDao.updateQuizById(id, newQuiz, studentId, subjectId, termId);
    }

    @Override
    public Quiz deleteQuizById(long id) throws GradingFactorException {
        return quizDao.deleteQuizById(id);
    }
}