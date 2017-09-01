package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.QuizDao;
import com.remswork.project.alice.dao.exception.GradingFactorDaoException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Quiz;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.model.Subject;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QuizDaoImpl implements QuizDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Quiz getQuizById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Quiz quiz = session.get(Quiz.class, id);
            if(quiz == null)
                throw new GradingFactorDaoException("Quiz with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return quiz;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Quiz> getQuizList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Quiz> quizList = new ArrayList<>();
            Query query = session.createQuery("from Quiz");
            for (Object objQuiz : query.list())
                quizList.add((Quiz) objQuiz);
            session.getTransaction().commit();
            session.close();
            return quizList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Quiz> getQuizListByStudentAndSubjectId(long studentId, long subjectId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Quiz> quizList = new ArrayList<>();
            Query query =
                    session.createQuery("from Quiz where student.id = :studentId and subject.id = :subjectId");
            query.setParameter("studentId", studentId);
            query.setParameter("subjectId", subjectId);
            for (Object objQuiz : query.list())
                quizList.add((Quiz) objQuiz);
            session.getTransaction().commit();
            session.close();
            return quizList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Quiz addQuiz(Quiz quiz, long studentId, long subjectId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, subjectId);

            if (quiz == null)
                throw new GradingFactorException("You tried to add class with a null value");
            if (studentId == 0)
                throw new GradingFactorException("Query param : studentId is required");
            if (subjectId == 0)
                throw new GradingFactorException("Query param : subjectId is required");
            if (student == null)
                throw new GradingFactorException("Quiz's student with id : " + studentId + " does not exist");
            if (subject == null)
                throw new GradingFactorException("Quiz's subject with id : " + subjectId + " does not exist");
            if (quiz.getTitle() == null)
                throw new GradingFactorException("Quiz's title is required");
            if (quiz.getTitle().trim().equals(""))
                throw new GradingFactorException("Quiz can't have an empty title");
            if (quiz.getDate() == null)
                throw new GradingFactorException("Quiz's date is required");
            if (quiz.getDate().trim().equals(""))
                throw new GradingFactorException("Quiz can't have an empty date");
            if(quiz.getItemTotal() < 0)
                throw new GradingFactorException("Quiz's itemTotal is invalid");
            if(quiz.getScore() < 0 && quiz.getScore() > quiz.getItemTotal())
                throw new GradingFactorException("Quiz's score is invalid");

            quiz.setStudent(student);
            quiz.setSubject(subject);

            session.persist(quiz);
            session.getTransaction().commit();
            session.close();
            return quiz;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Quiz updateQuizById(long id, Quiz newQuiz, long studentId, long subjectId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Quiz quiz = session.get(Quiz.class, id);
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, studentId);

            if(newQuiz == null)
                newQuiz = new Quiz();
            if(quiz == null)
                throw new GradingFactorException("Quiz with id : " + id + " does not exist");
            if (student == null && studentId != 0)
                throw new GradingFactorException("Quiz's student with id : " + studentId + " does not exist");
            if (subject == null && subjectId != 0)
                throw new GradingFactorException("Quiz's subject with id : " + subjectId + " does not exist");
            if(!(newQuiz.getTitle() != null ? newQuiz.getTitle() : "").trim().isEmpty())
                quiz.setTitle(newQuiz.getTitle());
            if(!(newQuiz.getDate() != null ? newQuiz.getDate() : "").trim().isEmpty())
                quiz.setDate(newQuiz.getDate());
            if(studentId > 0) {
                if(studentId == (quiz.getStudent() != null ? quiz.getStudent().getId() : 0))
                    throw new GradingFactorException("Quiz's  student with id : " + id + " already exist");
                quiz.setStudent(student);
            }
            if(subjectId > 0) {
                if(subjectId == (quiz.getSubject() != null ? quiz.getSubject().getId() : 0))
                    throw new GradingFactorException("Quiz's  student with id : " + id + " already exist");
                quiz.setSubject(subject);
            }
            session.getTransaction().commit();
            session.close();
            return quiz;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Quiz deleteQuizById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Quiz quiz = session.get(Quiz.class, id);
            if(quiz == null)
                throw new GradingFactorDaoException("Quiz with id : " + id + " does not exist");
            session.delete(quiz);
            session.getTransaction().commit();
            session.close();
            return quiz;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }
}
