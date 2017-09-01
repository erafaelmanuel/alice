package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.ActivityDao;
import com.remswork.project.alice.dao.exception.GradingFactorDaoException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Activity;
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
public class ActivityDaoImpl implements ActivityDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Activity getActivityById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Activity activity = session.get(Activity.class, id);
            if(activity == null)
                throw new GradingFactorDaoException("Activity with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return activity;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Activity> getActivityList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Activity> activityList = new ArrayList<>();
            Query query = session.createQuery("from Activity");
            for (Object objActivity : query.list())
                activityList.add((Activity) objActivity);
            session.getTransaction().commit();
            session.close();
            return activityList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Activity> getActivityListByStudentAndSubjectId(long studentId, long subjectId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Activity> activityList = new ArrayList<>();
            Query query =
                    session.createQuery("from Activity where student.id = :studentId and subject.id = :subjectId");
            query.setParameter("studentId", studentId);
            query.setParameter("subjectId", subjectId);
            for (Object objActivity : query.list())
                activityList.add((Activity) objActivity);
            session.getTransaction().commit();
            session.close();
            return activityList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Activity addActivity(Activity activity, long studentId, long subjectId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, subjectId);

            if (activity == null)
                throw new GradingFactorException("You tried to add class with a null value");
            if (studentId == 0)
                throw new GradingFactorException("Query param : studentId is required");
            if (subjectId == 0)
                throw new GradingFactorException("Query param : subjectId is required");
            if (student == null)
                throw new GradingFactorException("Activity's student with id : " + studentId + " does not exist");
            if (subject == null)
                throw new GradingFactorException("Activity's subject with id : " + subjectId + " does not exist");
            if (activity.getTitle() == null)
                throw new GradingFactorException("Activity's title is required");
            if (activity.getTitle().trim().equals(""))
                throw new GradingFactorException("Activity can't have an empty title");
            if (activity.getDate() == null)
                throw new GradingFactorException("Activity's date is required");
            if (activity.getDate().trim().equals(""))
                throw new GradingFactorException("Activity can't have an empty date");
            if(activity.getItemTotal() < 0)
                throw new GradingFactorException("Activity's itemTotal is invalid");
            if(activity.getScore() < 0 && activity.getScore() > activity.getItemTotal())
                throw new GradingFactorException("Activity's score is invalid");

            activity.setStudent(student);
            activity.setSubject(subject);

            session.persist(activity);
            session.getTransaction().commit();
            session.close();
            return activity;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Activity updateActivityById(long id, Activity newActivity, long studentId, long subjectId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Activity activity = session.get(Activity.class, id);
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, studentId);

            if(newActivity == null)
                newActivity = new Activity();
            if(activity == null)
                throw new GradingFactorException("Activity with id : " + id + " does not exist");
            if (student == null && studentId != 0)
                throw new GradingFactorException("Activity's student with id : " + studentId + " does not exist");
            if (subject == null && subjectId != 0)
                throw new GradingFactorException("Activity's subject with id : " + subjectId + " does not exist");
            if(!(newActivity.getTitle() != null ? newActivity.getTitle() : "").trim().isEmpty())
                activity.setTitle(newActivity.getTitle());
            if(!(newActivity.getDate() != null ? newActivity.getDate() : "").trim().isEmpty())
                activity.setDate(newActivity.getDate());
            if(studentId > 0) {
                if(studentId == (activity.getStudent() != null ? activity.getStudent().getId() : 0))
                    throw new GradingFactorException("Activity's  student with id : " + id + " already exist");
                activity.setStudent(student);
            }
            if(subjectId > 0) {
                if(subjectId == (activity.getSubject() != null ? activity.getSubject().getId() : 0))
                    throw new GradingFactorException("Activity's  student with id : " + id + " already exist");
                activity.setSubject(subject);
            }
            session.getTransaction().commit();
            session.close();
            return activity;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Activity deleteActivityById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Activity activity = session.get(Activity.class, id);
            if(activity == null)
                throw new GradingFactorDaoException("Activity with id : " + id + " does not exist");
            session.delete(activity);
            session.getTransaction().commit();
            session.close();
            return activity;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }
}
