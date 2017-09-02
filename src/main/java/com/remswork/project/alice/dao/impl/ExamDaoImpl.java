package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.ExamDao;
import com.remswork.project.alice.dao.exception.GradingFactorDaoException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Exam;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.model.Term;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExamDaoImpl implements ExamDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Exam getExamById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Exam exam = session.get(Exam.class, id);
            if(exam == null)
                throw new GradingFactorDaoException("Exam with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return exam;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Exam> getExamList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Exam> examList = new ArrayList<>();
            Query query = session.createQuery("from Exam");
            for (Object objExam : query.list())
                examList.add((Exam) objExam);
            session.getTransaction().commit();
            session.close();
            return examList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Exam> getExamListByStudentAndSubjectId(long studentId, long subjectId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Exam> examList = new ArrayList<>();
            Query query =
                    session.createQuery("from Exam where student.id = :studentId and subject.id = :subjectId");
            query.setParameter("studentId", studentId);
            query.setParameter("subjectId", subjectId);
            for (Object objExam : query.list())
                examList.add((Exam) objExam);
            session.getTransaction().commit();
            session.close();
            return examList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Exam> getExamListByStudentAndSubjectId(long studentId, long subjectId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Exam> examList = new ArrayList<>();
            String hql =
                    "from Exam where student.id = :studentId and subject.id = :subjectId and term.id = :termId";
            Query query =
                    session.createQuery(hql);
            query.setParameter("studentId", studentId);
            query.setParameter("subjectId", subjectId);
            query.setParameter("termId", termId);
            for (Object objExam : query.list())
                examList.add((Exam) objExam);
            session.getTransaction().commit();
            session.close();
            return examList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Exam addExam(Exam exam, long studentId, long subjectId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, subjectId);

            if (exam == null)
                throw new GradingFactorException("You tried to add class with a null value");
            if (studentId == 0)
                throw new GradingFactorException("Query param : studentId is required");
            if (subjectId == 0)
                throw new GradingFactorException("Query param : subjectId is required");
            if (student == null)
                throw new GradingFactorException("Exam's student with id : " + studentId + " does not exist");
            if (subject == null)
                throw new GradingFactorException("Exam's subject with id : " + subjectId + " does not exist");
            if (exam.getTitle() == null)
                throw new GradingFactorException("Exam's title is required");
            if (exam.getTitle().trim().equals(""))
                throw new GradingFactorException("Exam can't have an empty title");
            if (exam.getDate() == null)
                throw new GradingFactorException("Exam's date is required");
            if (exam.getDate().trim().equals(""))
                throw new GradingFactorException("Exam can't have an empty date");
            if(exam.getItemTotal() < 0)
                throw new GradingFactorException("Exam's itemTotal is invalid");
            if(exam.getScore() < 0 && exam.getScore() > exam.getItemTotal())
                throw new GradingFactorException("Exam's score is invalid");

            exam.setStudent(student);
            exam.setSubject(subject);

            session.persist(exam);
            session.getTransaction().commit();
            session.close();
            return exam;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Exam addExam(Exam exam, long studentId, long subjectId, long termId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, subjectId);
            Term term = session.get(Term.class, termId);

            if (exam == null)
                throw new GradingFactorException("You tried to add class with a null value");
            if (studentId == 0)
                throw new GradingFactorException("Query param : studentId is required");
            if (subjectId == 0)
                throw new GradingFactorException("Query param : subjectId is required");
            if (termId < 1)
                throw new GradingFactorException("Query param : termId has in invalid");
            if (student == null)
                throw new GradingFactorException("Exam's student with id : " + studentId + " does not exist");
            if (subject == null)
                throw new GradingFactorException("Exam's subject with id : " + subjectId + " does not exist");
            if (term == null)
                throw new GradingFactorException("Exam's term with id : " + termId + " does not exist");
            if (exam.getTitle() == null)
                throw new GradingFactorException("Exam's title is required");
            if (exam.getTitle().trim().equals(""))
                throw new GradingFactorException("Exam can't have an empty title");
            if (exam.getDate() == null)
                throw new GradingFactorException("Exam's date is required");
            if (exam.getDate().trim().equals(""))
                throw new GradingFactorException("Exam can't have an empty date");
            if(exam.getItemTotal() < 0)
                throw new GradingFactorException("Exam's itemTotal is invalid");
            if(exam.getScore() < 0 && exam.getScore() > exam.getItemTotal())
                throw new GradingFactorException("Exam's score is invalid");

            exam.setStudent(student);
            exam.setSubject(subject);
            exam.setTerm(term);

            session.persist(exam);
            session.getTransaction().commit();
            session.close();
            return exam;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Exam updateExamById(long id, Exam newExam, long studentId, long subjectId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Exam exam = session.get(Exam.class, id);
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, subjectId);

            if(newExam == null)
                newExam = new Exam();
            if(exam == null)
                throw new GradingFactorException("Exam with id : " + id + " does not exist");
            if (student == null && studentId != 0)
                throw new GradingFactorException("Exam's student with id : " + studentId + " does not exist");
            if (subject == null && subjectId != 0)
                throw new GradingFactorException("Exam's subject with id : " + subjectId + " does not exist");
            if(!(newExam.getTitle() != null ? newExam.getTitle() : "").trim().isEmpty())
                exam.setTitle(newExam.getTitle());
            if(!(newExam.getDate() != null ? newExam.getDate() : "").trim().isEmpty())
                exam.setDate(newExam.getDate());
            if(studentId > 0) {
                if(studentId == (exam.getStudent() != null ? exam.getStudent().getId() : 0))
                    throw new GradingFactorException("Exam's  student with id : " + id + " already exist");
                exam.setStudent(student);
            }
            if(subjectId > 0) {
                if(subjectId == (exam.getSubject() != null ? exam.getSubject().getId() : 0))
                    throw new GradingFactorException("Exam's  student with id : " + id + " already exist");
                exam.setSubject(subject);
            }
            session.getTransaction().commit();
            session.close();
            return exam;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Exam updateExamById(long id, Exam newExam, long studentId, long subjectId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Exam exam = session.get(Exam.class, id);
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, subjectId);
            Term term = session.get(Term.class, termId);

            if(newExam == null)
                newExam = new Exam();
            if(exam == null)
                throw new GradingFactorException("Exam with id : " + id + " does not exist");
            if (student == null && studentId != 0)
                throw new GradingFactorException("Exam's student with id : " + studentId + " does not exist");
            if (subject == null && subjectId != 0)
                throw new GradingFactorException("Exam's subject with id : " + subjectId + " does not exist");
            if (term == null && termId > 0)
                throw new GradingFactorException("Exam's term with id : " + termId + " does not exist");
            if(!(newExam.getTitle() != null ? newExam.getTitle() : "").trim().isEmpty())
                exam.setTitle(newExam.getTitle());
            if(!(newExam.getDate() != null ? newExam.getDate() : "").trim().isEmpty())
                exam.setDate(newExam.getDate());
            if(studentId > 0) {
                if(studentId == (exam.getStudent() != null ? exam.getStudent().getId() : 0))
                    throw new GradingFactorException("Exam's  student with id : " + id + " already exist");
                exam.setStudent(student);
            }
            if(subjectId > 0) {
                if(subjectId == (exam.getSubject() != null ? exam.getSubject().getId() : 0))
                    throw new GradingFactorException("Exam's  student with id : " + id + " already exist");
                exam.setSubject(subject);
            }
            if(termId > 0) {
                if(termId != (exam.getTerm() != null ? exam.getTerm().getId() : 0))
                    exam.setTerm(term);
            }

            session.getTransaction().commit();
            session.close();
            return exam;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Exam deleteExamById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Exam exam = session.get(Exam.class, id);
            if(exam == null)
                throw new GradingFactorDaoException("Exam with id : " + id + " does not exist");
            session.delete(exam);
            session.getTransaction().commit();
            session.close();
            return exam;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }
}