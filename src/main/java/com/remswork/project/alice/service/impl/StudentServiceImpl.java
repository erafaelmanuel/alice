package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.StudentDaoImpl;
import com.remswork.project.alice.exception.StudentException;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentDaoImpl studentDao;

    @Override
    public Student getStudentById(long id) throws StudentException {
        Student student = studentDao.getStudentById(id);
        if(student == null)
            throw new StudentException("No student to return");
        return student;
    }

    @Override
    public List<Student> getStudentList() throws StudentException {
        List<Student> studentList = studentDao.getStudentList();
        if(studentList == null)
            throw new StudentException("No student to return");
        return studentList;
    }

    @Override
    public Student addStudent(Student student) throws StudentException {
        Student s = studentDao.addStudent(student);
        if(s == null)
            throw new StudentException("No student to return");
        return s;
    }

    @Override
    public Student updateStudentById(long id, Student newStudent) throws StudentException {
        Student student = studentDao.updateStudentById(id, newStudent);
        if(student == null)
            throw new StudentException("No student to return");
        return student;
    }

    @Override
    public Student deleteStudentById(long id) throws StudentException {
        Student student = studentDao.deleteStudentById(id);
        if(student == null)
            throw new StudentException("No student to return");
        return student;
    }
}
