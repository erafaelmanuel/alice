package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.StudentDao;
import com.remswork.project.alice.dao.exception.StudentDaoException;
import com.remswork.project.alice.model.Student;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDaoImpl implements StudentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Student getStudentById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            Student student = session.get(Student.class, id);
            if(student == null)
                throw new StudentDaoException("Student with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return student;
        }catch (StudentDaoException e){
            e.printStackTrace();
            session.close();
            return null;
        }
    }

    @Override
    public List<Student> getStudentList() {
        List<Student> studentList = new ArrayList<>();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            Query query = session.createQuery("from Student");
            for(Object studentObj : query.list())
                studentList.add((Student) studentObj);
            session.getTransaction().commit();
            session.close();
            return studentList;
        }catch (StudentDaoException e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }

    @Override
    public Student addStudent(Student student) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            if (student == null)
                throw new StudentDaoException("You tried to add student with a null value");
            if (student.getStudentNumber() == 0)
                throw new StudentDaoException("Student's \"studentnumber\" is required");
            if (student.getFirstName() == null)
                throw new StudentDaoException("Student's first name is required");
            if (student.getFirstName().trim().equals(""))
                throw new StudentDaoException("Student can't have an empty first name");
            if (student.getLastName() == null)
                throw new StudentDaoException("Student's last name is required");
            if (student.getLastName().trim().equals(""))
                throw new StudentDaoException("Student can't have an empty last name");
            if (student.getMiddleName() == null)
                throw new StudentDaoException("Student's middle name is required");
            if (student.getMiddleName().trim().equals(""))
                throw new StudentDaoException("Student can't have an empty middle name");
            if (student.getGender() == null)
                throw new StudentDaoException(
                        "Student's gender is required");
            if (student.getGender().trim().equals(""))
                throw new StudentDaoException("Student can't have an empty gender");
            if (!(student.getGender().trim().equalsIgnoreCase("Male") ||
                    student.getGender().trim().equalsIgnoreCase("Female")))
                throw new StudentDaoException("Student's gender is invalid");
            if (student.getAge() < 1)
                throw new StudentDaoException("Student's age is invalid");

            session.persist(student);
            session.getTransaction().commit();
            session.close();
           return student;
        }catch (StudentDaoException e){
           e.printStackTrace();
           session.close();
           return null;
        }
    }

    @Override
    public Student updateStudentById(long id, Student newStudent) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            if(newStudent == null)
                throw new StudentDaoException("You tried to update student with a null value");
            Student student = session.get(Student.class, id);
            if (student == null)
                throw new StudentDaoException("Student with id : " + id + " does not exist.");
            if (newStudent.getStudentNumber() < 1)
                student.setStudentNumber(newStudent.getStudentNumber());
            if (!(newStudent.getFirstName() != null ? newStudent.getFirstName() : "").trim().isEmpty())
                student.setFirstName(newStudent.getFirstName());
            if (!(newStudent.getLastName() != null ? newStudent.getLastName() : "").trim().isEmpty())
                student.setLastName(newStudent.getLastName());
            if (!(newStudent.getMiddleName() != null ? newStudent.getMiddleName() : "").trim().isEmpty())
                student.setMiddleName(newStudent.getMiddleName());
            if (!(newStudent.getGender() != null ? newStudent.getGender() : "").trim().isEmpty()) {
                if (!(newStudent.getGender().trim().equalsIgnoreCase("Male") ||
                        newStudent.getGender().trim().equalsIgnoreCase("Female")))
                    throw new StudentDaoException("Student's gender is invalid");
                student.setGender(newStudent.getGender());
            }
            if (newStudent.getAge() > 14)
                student.setAge(newStudent.getAge());

            session.getTransaction().commit();
            session.close();
            return student;
        }catch (StudentDaoException e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }

    @Override
    public Student deleteStudentById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, id);
            if (student == null)
                throw new StudentDaoException("Student with id : " + id + " does not exist.");
            session.delete(student);
            session.getTransaction().commit();
            session.close();
            return student;
        }catch (StudentDaoException e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }
}
