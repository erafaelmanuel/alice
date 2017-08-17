package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.TeacherDao;
import com.remswork.project.alice.dao.exception.TeacherDaoException;
import com.remswork.project.alice.model.Department;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.model.UserDetail;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Repository
public class TeacherDaoImpl implements TeacherDao {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private DepartmentDaoImpl departmentDao;

    @Override
    public Teacher getTeacherById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Teacher teacher = session.get(Teacher.class, id);
            if (teacher == null)
                throw new TeacherDaoException("Teacher with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return teacher;
        } catch (TeacherDaoException e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }

    @Override
    public List<Teacher> getTeacherList() {
        final List<Teacher> teacherList = new ArrayList<>();
        Query query = null;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            query = session.createQuery("from Teacher");
            for (Object teacherObj : query.list())
                teacherList.add((Teacher) teacherObj);
            session.getTransaction().commit();
            session.close();
            return teacherList;
        } catch (TeacherDaoException e) {
            e.printStackTrace();
            session.close();
            return teacherList;
        }
    }

    @Override
    public Teacher addTeacher(Teacher teacher) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            if (teacher == null)
                throw new TeacherDaoException(
                        "You tried to add teacher with a null value");
            if (teacher.getFirstName() == null)
                throw new TeacherDaoException(
                        "Teacher's first name is required");
            if (teacher.getFirstName().trim().equals(""))
                throw new TeacherDaoException(
                        "Teacher can't have an empty first name");
            if (teacher.getLastName() == null)
                throw new TeacherDaoException(
                        "Teacher's last name is required");
            if (teacher.getLastName().trim().equals(""))
                throw new TeacherDaoException(
                        "Teacher can't have an empty last name");
            if (teacher.getMiddleName() == null)
                throw new TeacherDaoException(
                        "Teacher's middle name is required");
            if (teacher.getMiddleName().trim().equals(""))
                throw new TeacherDaoException(
                        "Teacher can't have an empty middle name");
            if (teacher.getEmail() == null)
                throw new TeacherDaoException(
                        "Teacher's email is required");
            if (teacher.getEmail().trim().equals(""))
                throw new TeacherDaoException(
                        "Teacher can't have an empty email");

            UserDetail userDetail = new UserDetail();
            userDetail.setIsEnabled(true);
            userDetail.setRegistered(Calendar.getInstance().getTime().toString());
            userDetail.setUsername(teacher.getEmail());
            userDetail.setPassword((teacher.getFirstName() + teacher.getLastName()+"123").toLowerCase());
            userDetail.setUserType(UserDetail.USER);
            teacher.setUserDetail(userDetail);

            session.persist(teacher);
            session.getTransaction().commit();
            session.close();
            return teacher;
        } catch (TeacherDaoException e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }

    @Override
    public Teacher addTeacher(Teacher teacher, long departmentId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            if (teacher == null)
                throw new TeacherDaoException(
                        "You tried to add teacher with a null value");
            if (teacher.getFirstName() == null)
                throw new TeacherDaoException(
                        "Teacher's first name is required");
            if (teacher.getFirstName().trim().equals(""))
                throw new TeacherDaoException(
                        "Teacher can't have an empty first name");
            if (teacher.getLastName() == null)
                throw new TeacherDaoException(
                        "Teacher's last name is required");
            if (teacher.getLastName().trim().equals(""))
                throw new TeacherDaoException(
                        "Teacher can't have an empty last name");
            if (teacher.getMiddleName() == null)
                throw new TeacherDaoException(
                        "Teacher's middle name is required");
            if (teacher.getMiddleName().trim().equals(""))
                throw new TeacherDaoException(
                        "Teacher can't have an empty middle name");
            if (teacher.getEmail() == null)
                throw new TeacherDaoException(
                        "Teacher's email is required");
            if (teacher.getEmail().trim().equals(""))
                throw new TeacherDaoException(
                        "Teacher can't have an empty email");

            if (departmentId > 0) {
                Department department = departmentDao.getDepartmentById(departmentId);
                if (department != null)
                    teacher.setDepartment(department);
            }

            UserDetail userDetail = new UserDetail();
            userDetail.setIsEnabled(true);
            userDetail.setRegistered(Calendar.getInstance().getTime().toString());
            userDetail.setUsername(teacher.getEmail());
            userDetail.setPassword((teacher.getFirstName() + teacher.getLastName()+"123").toLowerCase());
            userDetail.setUserType(UserDetail.USER);
            teacher.setUserDetail(userDetail);

            teacher = (Teacher) session.merge(teacher);
            session.getTransaction().commit();
            session.close();
            return teacher;
        } catch (TeacherDaoException e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }

    @Override
    public Teacher updateTeacherById(long id, Teacher newTeacher) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            if (newTeacher == null)
                throw new TeacherDaoException(
                        "You tried to update teacher with a null value");
            Teacher teacher = session.get(Teacher.class, id);

            if (teacher == null)
                throw new TeacherDaoException("Teacher with id : " + id + " does not exist.");
            if (!(newTeacher.getFirstName() != null ? newTeacher.getFirstName() : "").trim().isEmpty())
                teacher.setFirstName(newTeacher.getFirstName());
            if (!(newTeacher.getLastName() != null ? newTeacher.getLastName() : "").trim().isEmpty())
                teacher.setLastName(newTeacher.getLastName());
            if (!(newTeacher.getEmail() != null ? newTeacher.getEmail() : "").trim().isEmpty())
                teacher.setEmail(newTeacher.getEmail());
            if (!(newTeacher.getMiddleName() != null ? newTeacher.getMiddleName() : "").trim().isEmpty())
                teacher.setMiddleName(newTeacher.getMiddleName());

            session.getTransaction().commit();
            session.close();
            return teacher;
        } catch (TeacherDaoException e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }

    @Override
    public Teacher updateTeacherById(long id, Teacher newTeacher, long departmentId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            if (newTeacher == null)
                throw new TeacherDaoException(
                        "You tried to update teacher with a null value");
            Teacher teacher = session.get(Teacher.class, id);

            if (teacher == null)
                throw new TeacherDaoException("Teacher with id : " + id + " does not exist.");
            if (!(newTeacher.getFirstName() != null ? newTeacher.getFirstName() : "").trim().isEmpty())
                teacher.setFirstName(newTeacher.getFirstName());
            if (!(newTeacher.getLastName() != null ? newTeacher.getLastName() : "").trim().isEmpty())
                teacher.setLastName(newTeacher.getLastName());
            if (!(newTeacher.getEmail() != null ? newTeacher.getEmail() : "").trim().isEmpty())
                teacher.setEmail(newTeacher.getEmail());
            if (!(newTeacher.getMiddleName() != null ? newTeacher.getMiddleName() : "").trim().isEmpty())
                teacher.setMiddleName(newTeacher.getMiddleName());
            if (departmentId > 0) {
                Department department = departmentDao.getDepartmentById(departmentId);
                if (department != null) {
                    teacher.setDepartment(department);
                    teacher = (Teacher) session.merge(teacher);
                }
            }
            session.getTransaction().commit();
            session.close();
            return teacher;
        } catch (TeacherDaoException e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }

    @Override
    public Teacher deleteTeacherById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Teacher teacher = session.get(Teacher.class, id);
            if (teacher != null)
                session.delete(teacher);
            else
                throw new TeacherDaoException("Teacher with id : " + id + " does not exist.");

            session.getTransaction().commit();
            session.close();
            return teacher;
        } catch (TeacherDaoException e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }

}
