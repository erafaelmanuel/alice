package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.StudentDaoImpl;
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
    public Student getStudentById(long id) {
        return studentDao.getStudentById(id);
    }

    @Override
    public List<Student> getStudentList() {
        return studentDao.getStudentList();
    }

    @Override
    public Student addStudent(Student student) {
        return studentDao.addStudent(student);
    }

    @Override
    public Student updateStudentById(long id, Student newStudent) {
        return studentDao.updateStudentById(id, newStudent);
    }

    @Override
    public Student deleteStudentById(long id) {
        return studentDao.deleteStudentById(id);
    }
}
