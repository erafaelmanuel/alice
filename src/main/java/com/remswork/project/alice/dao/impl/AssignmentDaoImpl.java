package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.AssignmentDao;
import com.remswork.project.alice.dao.exception.GradingFactorDaoException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Assignment;
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
public class AssignmentDaoImpl implements AssignmentDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Assignment getAssignmentById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Assignment assignment = session.get(Assignment.class, id);
            if(assignment == null)
                throw new GradingFactorDaoException("Assignment with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return assignment;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Assignment> getAssignmentList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Assignment> assignmentList = new ArrayList<>();
            Query query = session.createQuery("from Assignment");
            for (Object objAssignment : query.list())
                assignmentList.add((Assignment) objAssignment);
            session.getTransaction().commit();
            session.close();
            return assignmentList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Assignment> getAssignmentListByStudentAndSubjectId(long studentId, long subjectId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Assignment> assignmentList = new ArrayList<>();
            Query query =
                    session.createQuery("from Assignment where student.id = :studentId and subject.id = :subjectId");
            query.setParameter("studentId", studentId);
            query.setParameter("subjectId", subjectId);
            for (Object objAssignment : query.list())
                assignmentList.add((Assignment) objAssignment);
            session.getTransaction().commit();
            session.close();
            return assignmentList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Assignment> getAssignmentListByStudentAndSubjectId(long studentId, long subjectId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Assignment> assignmentList = new ArrayList<>();
            String hql =
                    "from Assignment where student.id = :studentId and subject.id = :subjectId and term.id = :termId";
            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            query.setParameter("subjectId", subjectId);
            query.setParameter("termId", termId);
            for (Object objAssignment : query.list())
                assignmentList.add((Assignment) objAssignment);
            session.getTransaction().commit();
            session.close();
            return assignmentList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Assignment addAssignment(Assignment assignment, long studentId, long subjectId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, subjectId);

            if (assignment == null)
                throw new GradingFactorException("You tried to add class with a null value");
            if (studentId == 0)
                throw new GradingFactorException("Query param : studentId is required");
            if (subjectId == 0)
                throw new GradingFactorException("Query param : subjectId is required");
            if (student == null)
                throw new GradingFactorException("Assignment's student with id : " + studentId + " does not exist");
            if (subject == null)
                throw new GradingFactorException("Assignment's subject with id : " + subjectId + " does not exist");
            if (assignment.getTitle() == null)
                throw new GradingFactorException("Assignment's title is required");
            if (assignment.getTitle().trim().equals(""))
                throw new GradingFactorException("Assignment can't have an empty title");
            if (assignment.getDate() == null)
                throw new GradingFactorException("Assignment's date is required");
            if (assignment.getDate().trim().equals(""))
                throw new GradingFactorException("Assignment can't have an empty date");
            if(assignment.getItemTotal() < 0)
                throw new GradingFactorException("Assignment's itemTotal is invalid");
            if(assignment.getScore() < 0 && assignment.getScore() > assignment.getItemTotal())
                throw new GradingFactorException("Assignment's score is invalid");

            assignment.setStudent(student);
            assignment.setSubject(subject);

            session.persist(assignment);
            session.getTransaction().commit();
            session.close();
            return assignment;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Assignment addAssignment(Assignment assignment, long studentId, long subjectId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, subjectId);
            Term term = session.get(Term.class, termId);

            if (assignment == null)
                throw new GradingFactorException("You tried to add class with a null value");
            if (studentId == 0)
                throw new GradingFactorException("Query param : studentId is required");
            if (subjectId == 0)
                throw new GradingFactorException("Query param : subjectId is required");
            if (termId < 1)
                throw new GradingFactorException("Query param : termId has in invalid");
            if (student == null)
                throw new GradingFactorException("Assignment's student with id : " + studentId + " does not exist");
            if (subject == null)
                throw new GradingFactorException("Assignment's subject with id : " + subjectId + " does not exist");
            if (term == null)
                throw new GradingFactorException("Assignment's term with id : " + termId + " does not exist");
            if (assignment.getTitle() == null)
                throw new GradingFactorException("Assignment's title is required");
            if (assignment.getTitle().trim().equals(""))
                throw new GradingFactorException("Assignment can't have an empty title");
            if (assignment.getDate() == null)
                throw new GradingFactorException("Assignment's date is required");
            if (assignment.getDate().trim().equals(""))
                throw new GradingFactorException("Assignment can't have an empty date");
            if(assignment.getItemTotal() < 0)
                throw new GradingFactorException("Assignment's itemTotal is invalid");
            if(assignment.getScore() < 0 && assignment.getScore() > assignment.getItemTotal())
                throw new GradingFactorException("Assignment's score is invalid");

            assignment.setStudent(student);
            assignment.setSubject(subject);
            assignment.setTerm(term);

            session.persist(assignment);
            session.getTransaction().commit();
            session.close();
            return assignment;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Assignment updateAssignmentById(long id, Assignment newAssignment, long studentId, long subjectId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Assignment assignment = session.get(Assignment.class, id);
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, subjectId);

            if(newAssignment == null)
                newAssignment = new Assignment();
            if(assignment == null)
                throw new GradingFactorException("Assignment with id : " + id + " does not exist");
            if (student == null && studentId != 0)
                throw new GradingFactorException("Assignment's student with id : " + studentId + " does not exist");
            if (subject == null && subjectId != 0)
                throw new GradingFactorException("Assignment's subject with id : " + subjectId + " does not exist");
            if(!(newAssignment.getTitle() != null ? newAssignment.getTitle() : "").trim().isEmpty())
                assignment.setTitle(newAssignment.getTitle());
            if(!(newAssignment.getDate() != null ? newAssignment.getDate() : "").trim().isEmpty())
                assignment.setDate(newAssignment.getDate());
            if(studentId > 0) {
                if(studentId == (assignment.getStudent() != null ? assignment.getStudent().getId() : 0))
                    throw new GradingFactorException("Assignment's  student with id : " + id + " already exist");
                assignment.setStudent(student);
            }
            if(subjectId > 0) {
                if(subjectId == (assignment.getSubject() != null ? assignment.getSubject().getId() : 0))
                    throw new GradingFactorException("Assignment's  student with id : " + id + " already exist");
                assignment.setSubject(subject);
            }
            session.getTransaction().commit();
            session.close();
            return assignment;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Assignment updateAssignmentById(long id, Assignment newAssignment, long studentId, long subjectId,
                                           long termId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Assignment assignment = session.get(Assignment.class, id);
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, subjectId);
            Term term = session.get(Term.class, termId);

            if(newAssignment == null)
                newAssignment = new Assignment();
            if(assignment == null)
                throw new GradingFactorException("Assignment with id : " + id + " does not exist");
            if (student == null && studentId != 0)
                throw new GradingFactorException("Assignment's student with id : " + studentId + " does not exist");
            if (subject == null && subjectId != 0)
                throw new GradingFactorException("Assignment's subject with id : " + subjectId + " does not exist");
            if (term == null && termId > 0)
                throw new GradingFactorException("Assignment's term with id : " + termId + " does not exist");
            if(!(newAssignment.getTitle() != null ? newAssignment.getTitle() : "").trim().isEmpty())
                assignment.setTitle(newAssignment.getTitle());
            if(!(newAssignment.getDate() != null ? newAssignment.getDate() : "").trim().isEmpty())
                assignment.setDate(newAssignment.getDate());
            if(studentId > 0) {
                if(studentId == (assignment.getStudent() != null ? assignment.getStudent().getId() : 0))
                    throw new GradingFactorException("Assignment's  student with id : " + id + " already exist");
                assignment.setStudent(student);
            }
            if(subjectId > 0) {
                if(subjectId == (assignment.getSubject() != null ? assignment.getSubject().getId() : 0))
                    throw new GradingFactorException("Assignment's  student with id : " + id + " already exist");
                assignment.setSubject(subject);
            }
            if(termId > 0) {
                if(termId != (assignment.getTerm() != null ? assignment.getTerm().getId() : 0))
                    assignment.setTerm(term);
            }
            session.getTransaction().commit();
            session.close();
            return assignment;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Assignment deleteAssignmentById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Assignment assignment = session.get(Assignment.class, id);
            if(assignment == null)
                throw new GradingFactorDaoException("Assignment with id : " + id + " does not exist");
            session.delete(assignment);
            session.getTransaction().commit();
            session.close();
            return assignment;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }
}