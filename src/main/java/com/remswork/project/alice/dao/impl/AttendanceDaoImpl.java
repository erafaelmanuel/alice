package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.AttendanceDao;
import com.remswork.project.alice.dao.exception.GradingFactorDaoException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Attendance;
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
public class AttendanceDaoImpl implements AttendanceDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Attendance getAttendanceById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Attendance attendance = session.get(Attendance.class, id);
            if(attendance == null)
                throw new GradingFactorDaoException("Attendance with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return attendance;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Attendance> getAttendanceList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Attendance> attendanceList = new ArrayList<>();
            Query query = session.createQuery("from Attendance");
            for (Object objAttendance : query.list())
                attendanceList.add((Attendance) objAttendance);
            session.getTransaction().commit();
            session.close();
            return attendanceList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Attendance> getAttendanceListByStudentAndSubjectId(long studentId, long subjectId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Attendance> attendanceList = new ArrayList<>();
            Query query =
                    session.createQuery("from Attendance where student.id = :studentId and subject.id = :subjectId");
            query.setParameter("studentId", studentId);
            query.setParameter("subjectId", subjectId);
            for (Object objAttendance : query.list())
                attendanceList.add((Attendance) objAttendance);
            session.getTransaction().commit();
            session.close();
            return attendanceList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Attendance addAttendance(Attendance attendance, long studentId, long subjectId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, subjectId);

            if (attendance == null)
                throw new GradingFactorException("You tried to add class with a null value");
            if (studentId == 0)
                throw new GradingFactorException("Query param : studentId is required");
            if (subjectId == 0)
                throw new GradingFactorException("Query param : subjectId is required");
            if (student == null)
                throw new GradingFactorException("Attendance's student with id : " + studentId + " does not exist");
            if (subject == null)
                throw new GradingFactorException("Attendance's subject with id : " + subjectId + " does not exist");
            if (attendance.getDate() == null)
                throw new GradingFactorException("Attendance's date is required");
            if (attendance.getDate().trim().equals(""))
                throw new GradingFactorException("Attendance can't have an empty date");
            if(attendance.getStatus() < 0)
                throw new GradingFactorException("Attendance's status is invalid");

            attendance.setStudent(student);
            attendance.setSubject(subject);

            session.persist(attendance);
            session.getTransaction().commit();
            session.close();
            return attendance;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Attendance updateAttendanceById(long id, Attendance newAttendance, long studentId, long subjectId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Attendance attendance = session.get(Attendance.class, id);
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, studentId);

            if(newAttendance == null)
                newAttendance = new Attendance();
            if(attendance == null)
                throw new GradingFactorException("Attendance with id : " + id + " does not exist");
            if (student == null && studentId != 0)
                throw new GradingFactorException("Attendance's student with id : " + studentId + " does not exist");
            if (subject == null && subjectId != 0)
                throw new GradingFactorException("Attendance's subject with id : " + subjectId + " does not exist");
            if(!(newAttendance.getDate() != null ? newAttendance.getDate() : "").trim().isEmpty())
                attendance.setDate(newAttendance.getDate());
            if(studentId > 0) {
                if(studentId == (attendance.getStudent() != null ? attendance.getStudent().getId() : 0))
                    throw new GradingFactorException("Attendance's  student with id : " + id + " already exist");
                attendance.setStudent(student);
            }
            if(subjectId > 0) {
                if(subjectId == (attendance.getSubject() != null ? attendance.getSubject().getId() : 0))
                    throw new GradingFactorException("Attendance's  student with id : " + id + " already exist");
                attendance.setSubject(subject);
            }
            session.getTransaction().commit();
            session.close();
            return attendance;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Attendance deleteAttendanceById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Attendance attendance = session.get(Attendance.class, id);
            if(attendance == null)
                throw new GradingFactorDaoException("Attendance with id : " + id + " does not exist");
            session.delete(attendance);
            session.getTransaction().commit();
            session.close();
            return attendance;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }
}