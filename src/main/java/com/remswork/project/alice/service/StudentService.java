package com.remswork.project.alice.service;

import com.remswork.project.alice.exception.StudentException;
import com.remswork.project.alice.model.Student;

import java.util.List;

public interface StudentService {

    Student getStudentById(long id) throws StudentException;
    List<Student> getStudentList() throws StudentException;
    Student addStudent(Student student) throws StudentException;
    Student updateStudentById(long id, Student newStudent) throws StudentException;
    Student deleteStudentById(long id) throws StudentException;
}
