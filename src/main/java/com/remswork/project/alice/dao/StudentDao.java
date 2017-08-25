package com.remswork.project.alice.dao;

import com.remswork.project.alice.model.Student;

import java.util.List;

public interface StudentDao {

    public Student getStudentById(long id);
    public List<Student> getStudentList();
    public Student addStudent(Student student);
    public Student updateStudentById(long id, Student newStudent);
    public Student deleteStudentById(long id);
}
