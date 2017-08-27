package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.ClassDao;
import com.remswork.project.alice.dao.exception.ClassDaoException;
import com.remswork.project.alice.exception.*;
import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class ClassDaoImpl implements ClassDao {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private TeacherDaoImpl teacherDao;
    @Autowired
    private SubjectDaoImpl subjectDao;
    @Autowired
    private ScheduleDaoImpl scheduleDao;
    @Autowired
    private SectionDaoImpl sectionDao;
    @Autowired
    private StudentDaoImpl studentDao;

    @Override
    public Class getClassById(long id) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, id);
            if (_class == null)
                throw new ClassDaoException("Class with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return _class;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public List<Class> getClassList() throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Class> classList = new ArrayList<>();
            Query query = session.createQuery("from Class");
            for (Object classObject : query.list())
                classList.add((Class) classObject);
            session.getTransaction().commit();
            session.close();
            return classList;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Schedule getScheduleById(long classId, long id) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            Schedule schedule = null;
            if (_class == null)
                throw new ClassDaoException("Class with id : " + classId + " does not exist");
            for (Schedule s : _class.getScheduleList()) {
                if (s.getId() == id)
                    schedule = s;
            }
            if (schedule == null)
                throw new ClassDaoException("Class's schedule with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return schedule;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Set<Schedule> getScheduleList(long classId) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            if (_class == null)
                throw new ClassDaoException("Class with id : " + classId + " does not exist");
            Set<Schedule> scheduleSet = _class.getScheduleList();
            session.getTransaction().commit();
            session.close();
            return scheduleSet;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Class addClass(Class _class, long teacherId, long subjectId, long sectionId) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            if (_class == null)
                throw new ClassDaoException("You tried to add class with a null value");

            _class.setTeacher(null);
            _class.setSubject(null);
            _class.setSection(null);

            if (teacherId != 0) {
                Teacher teacher = teacherDao.getTeacherById(teacherId);
                _class.setTeacher(teacher);
            }
            if (subjectId != 0) {
                Subject subject = subjectDao.getSubjectById(subjectId);
                _class.setSubject(subject);
            }
            if (sectionId != 0) {
                Section section = sectionDao.getSectionById(sectionId);
                _class.setSection(section);
            }
            _class = (Class) session.merge(_class);
            session.getTransaction().commit();
            session.close();
            return _class;
        } catch (ClassDaoException | TeacherException | SubjectException | SectionException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Schedule addScheduleById(long classId, long id) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            if (_class == null)
                throw new ClassDaoException("Class with id : " + classId + " does not exist");
            Schedule schedule = scheduleDao.getScheduleById(id);
            for (Schedule s : _class.getScheduleList()) {
                if (s.getId() == id)
                    throw new ClassDaoException("Class's schedule with id : " + id + " already exist");
            }
            _class.getScheduleList().add(schedule);
            session.getTransaction().commit();
            session.close();
            return schedule;
        } catch (ClassDaoException | ScheduleException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Class updateClassId(long id, Class newClass, long teacherId, long subjectId, long sectionId)
            throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, id);
            if (_class == null)
                throw new ClassDaoException("Class with id : " + id + " does not exist");
            if (teacherId != 0) {
                Teacher teacher = teacherDao.getTeacherById(teacherId);
                _class.setTeacher(teacher);
            }
            if (subjectId != 0) {
                Subject subject = subjectDao.getSubjectById(subjectId);
                _class.setSubject(subject);
            }
            if (sectionId != 0) {
                Section section = sectionDao.getSectionById(sectionId);
                _class.setSection(section);
            }
            session.getTransaction().commit();
            session.close();
            return _class;
        } catch (ClassDaoException | TeacherException | SubjectException | SectionException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Class deleteClassById(long id) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, id);
            if (_class == null)
                throw new ClassDaoException("Class with id : " + id + " does not exist");

            _class.setTeacher(null);
            _class.setSubject(null);
            _class.setScheduleList(null);
            _class.setSection(null);
            _class.setStudentList(null);

            session.delete(_class);
            session.getTransaction().commit();
            session.close();
            return _class;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }

    @Override
    public Schedule deleteScheduleById(long classId, long id) throws ClassException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Class _class = session.get(Class.class, classId);
            Schedule schedule = null;
            if (_class == null)
                throw new ClassDaoException("Class with id : " + classId + " does not exist");
            for (Schedule s : _class.getScheduleList()) {
                if (s.getId() == id) {
                    _class.getScheduleList().remove(s);
                    schedule = s;
                    break;
                }
            }
            if (schedule == null)
                throw new ClassDaoException("Class's schedule with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return schedule;
        } catch (ClassDaoException e) {
            session.close();
            throw new ClassException(e.getMessage());
        }
    }
}
