package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.GradeDao;
import com.remswork.project.alice.dao.exception.DepartmentDaoException;
import com.remswork.project.alice.dao.exception.GradingFactorDaoException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.model.Grade;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.model.Term;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GradeDaoImpl implements GradeDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Grade getGradeById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Grade grade = session.get(Grade.class, id);
            if(grade == null)
                throw new GradingFactorDaoException("Grade with id : " + id + " doesn't exist.");
            session.getTransaction().commit();
            session.close();
            return  grade;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Grade> getGradeList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Grade> gradeList = new ArrayList<>();
            Query query = session.createQuery("from Grade");

            for(Object object : query.list())
                gradeList.add((Grade) object);
            session.getTransaction().commit();
            session.close();
            return gradeList;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Grade> getGradeListByStudentId(long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Grade> gradeList = new ArrayList<>();
            String hql = "from Grade as G where G.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);

            for(Object object : query.list())
                gradeList.add((Grade) object);
            session.getTransaction().commit();
            session.close();
            return gradeList;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Grade> getGradeListByStudentId(long studentId, long termId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Grade> gradeList = new ArrayList<>();
            String hql = "from Grade as G where G.student.id = :studentId and G.term.id = :termId";
            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            query.setParameter("termId", termId);

            for(Object object : query.list())
                gradeList.add((Grade) object);
            session.getTransaction().commit();
            session.close();
            return gradeList;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Grade> getGradeListByClass(long classId, long studentId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Grade> gradeList = new ArrayList<>();
            String hql = "from Grade as G where G.class.id = :classId and G.student.id = :studentId";
            Query query = session.createQuery(hql);
            query.setParameter("classId", classId);
            query.setParameter("studentId", studentId);

            for(Object object : query.list())
                gradeList.add((Grade) object);
            session.getTransaction().commit();
            session.close();
            return gradeList;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Grade> getGradeListByClass(long classId, long studentId, long termId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Grade> gradeList = new ArrayList<>();
            String hql = "from Grade as G where G.class.id = :classId and G.student.id = :studentId " +
                    "and G.term.id = :termId";
            Query query = session.createQuery(hql);
            query.setParameter("classId", classId);
            query.setParameter("studentId", studentId);
            query.setParameter("termId", termId);

            for(Object object : query.list())
                gradeList.add((Grade) object);
            session.getTransaction().commit();
            session.close();
            return gradeList;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Grade addGrade(Grade grade, long classId, long studentId, long termId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            Student student = session.get(Student.class, studentId);
            Term term = session.get(Term.class, termId);

            if(grade == null)
                throw new GradingFactorDaoException("You tried to add department with a null value");
            if(grade.getScore() == 0)
                throw new GradingFactorDaoException("Grade's score is not valid");
            if(classId < 1)
                throw new GradingFactorDaoException("Query param : classId is required.");
            if(studentId < 1)
                throw new GradingFactorDaoException("Query param : studentId is required.");
            if(termId < 1)
                throw new GradingFactorDaoException("Query param : termId is required.");
            if(_class == null)
                throw new GradingFactorDaoException("Class with id : " + classId + " doesn't exist.");
            if(student == null)
                throw new GradingFactorDaoException("Student with id : " + studentId + " doesn't exist.");
            if(term == null)
                throw new GradingFactorDaoException("Term with id : " + termId + " doesn't exist.");

            session.persist(grade);
            session.getTransaction().commit();
            session.close();
            return grade;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Grade updateGradeById(long id, Grade newGrade) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Grade grade = session.get(Grade.class, id);
            if(newGrade == null)
                newGrade = new Grade();
            if(grade == null)
                throw new GradingFactorDaoException("Grade with id : " + id + " doesn't exist.");
            if(newGrade.getScore() > 0)
                grade.setScore(newGrade.getScore());
            session.getTransaction().commit();
            session.close();
            return grade;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Grade updateGradeByStudentId(long id, Grade newGrade, long classId, long studentId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Grade grade = session.get(Grade.class, id);
            Class _class = session.get(Class.class, classId);
            Student student = session.get(Student.class, studentId);

            if(newGrade == null)
                newGrade = new Grade();
            if(grade == null)
                throw new GradingFactorDaoException("Grade with id : " + id + " doesn't exist.");
            if(_class == null)
                throw new GradingFactorDaoException("Class with id : " + classId + " doesn't exist.");
            if(student == null)
                throw new GradingFactorDaoException("Student with id : " + studentId + " doesn't exist.");
            if(newGrade.getScore() > 0)
                grade.setScore(newGrade.getScore());

            grade.set_class(_class);
            grade.setStudent(student);
            session.getTransaction().commit();
            session.close();
            return grade;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Grade updateGradeByClass(long id, Grade newGrade, long classId, long studentId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Grade grade = session.get(Grade.class, id);
            Class _class = session.get(Class.class, classId);
            Student student = session.get(Student.class, studentId);
            Term term = session.get(Term.class, termId);

            if(newGrade == null)
                newGrade = new Grade();
            if(grade == null)
                throw new GradingFactorDaoException("Grade with id : " + id + " doesn't exist.");
            if(_class == null)
                throw new GradingFactorDaoException("Class with id : " + classId + " doesn't exist.");
            if(student == null)
                throw new GradingFactorDaoException("Student with id : " + studentId + " doesn't exist.");
            if(term == null)
                throw new GradingFactorDaoException("Term with id : " + termId + " doesn't exist.");
            if(newGrade.getScore() > 0)
                grade.setScore(newGrade.getScore());

            grade.set_class(_class);
            grade.setStudent(student);
            grade.setTerm(term);
            session.getTransaction().commit();
            session.close();
            return grade;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Grade deleteGradeById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Grade grade = session.get(Grade.class, id);
            if(grade == null)
                throw new GradingFactorDaoException("Grade with id : " + id + " doesn't exist.");
            session.delete(grade);
            session.getTransaction().commit();
            session.close();
            return  grade;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }
}
