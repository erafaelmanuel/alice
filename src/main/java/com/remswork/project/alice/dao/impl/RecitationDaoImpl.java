package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.RecitationDao;
import com.remswork.project.alice.dao.exception.GradingFactorDaoException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Recitation;
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
public class RecitationDaoImpl implements RecitationDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Recitation getRecitationById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Recitation recitation = session.get(Recitation.class, id);
            if(recitation == null)
                throw new GradingFactorDaoException("Recitation with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return recitation;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Recitation> getRecitationList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Recitation> recitationList = new ArrayList<>();
            Query query = session.createQuery("from Recitation");
            for (Object objRecitation : query.list())
                recitationList.add((Recitation) objRecitation);
            session.getTransaction().commit();
            session.close();
            return recitationList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Recitation> getRecitationListByStudentAndSubjectId(long studentId, long subjectId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Recitation> recitationList = new ArrayList<>();
            Query query =
                    session.createQuery("from Recitation where student.id = :studentId and subject.id = :subjectId");
            query.setParameter("studentId", studentId);
            query.setParameter("subjectId", subjectId);
            for (Object objRecitation : query.list())
                recitationList.add((Recitation) objRecitation);
            session.getTransaction().commit();
            session.close();
            return recitationList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Recitation addRecitation(Recitation recitation, long studentId, long subjectId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, subjectId);

            if (recitation == null)
                throw new GradingFactorException("You tried to add class with a null value");
            if (studentId == 0)
                throw new GradingFactorException("Query param : studentId is required");
            if (subjectId == 0)
                throw new GradingFactorException("Query param : subjectId is required");
            if (student == null)
                throw new GradingFactorException("Recitation's student with id : " + studentId + " does not exist");
            if (subject == null)
                throw new GradingFactorException("Recitation's subject with id : " + subjectId + " does not exist");
            if (recitation.getTitle() == null)
                throw new GradingFactorException("Recitation's title is required");
            if (recitation.getTitle().trim().equals(""))
                throw new GradingFactorException("Recitation can't have an empty title");
            if (recitation.getDate() == null)
                throw new GradingFactorException("Recitation's date is required");
            if (recitation.getDate().trim().equals(""))
                throw new GradingFactorException("Recitation can't have an empty date");
            if(recitation.getItemTotal() < 0)
                throw new GradingFactorException("Recitation's itemTotal is invalid");
            if(recitation.getScore() < 0 && recitation.getScore() > recitation.getItemTotal())
                throw new GradingFactorException("Recitation's score is invalid");

            recitation.setStudent(student);
            recitation.setSubject(subject);

            session.persist(recitation);
            session.getTransaction().commit();
            session.close();
            return recitation;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Recitation updateRecitationById(long id, Recitation newRecitation, long studentId, long subjectId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Recitation recitation = session.get(Recitation.class, id);
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, studentId);

            if(newRecitation == null)
                newRecitation = new Recitation();
            if(recitation == null)
                throw new GradingFactorException("Recitation with id : " + id + " does not exist");
            if (student == null && studentId != 0)
                throw new GradingFactorException("Recitation's student with id : " + studentId + " does not exist");
            if (subject == null && subjectId != 0)
                throw new GradingFactorException("Recitation's subject with id : " + subjectId + " does not exist");
            if(!(newRecitation.getTitle() != null ? newRecitation.getTitle() : "").trim().isEmpty())
                recitation.setTitle(newRecitation.getTitle());
            if(!(newRecitation.getDate() != null ? newRecitation.getDate() : "").trim().isEmpty())
                recitation.setDate(newRecitation.getDate());
            if(studentId > 0) {
                if(studentId == (recitation.getStudent() != null ? recitation.getStudent().getId() : 0))
                    throw new GradingFactorException("Recitation's  student with id : " + id + " already exist");
                recitation.setStudent(student);
            }
            if(subjectId > 0) {
                if(subjectId == (recitation.getSubject() != null ? recitation.getSubject().getId() : 0))
                    throw new GradingFactorException("Recitation's  student with id : " + id + " already exist");
                recitation.setSubject(subject);
            }
            session.getTransaction().commit();
            session.close();
            return recitation;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Recitation deleteRecitationById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Recitation recitation = session.get(Recitation.class, id);
            if(recitation == null)
                throw new GradingFactorDaoException("Recitation with id : " + id + " does not exist");
            session.delete(recitation);
            session.getTransaction().commit();
            session.close();
            return recitation;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }
}