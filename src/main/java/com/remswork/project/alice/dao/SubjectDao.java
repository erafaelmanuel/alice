package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.SubjectException;
import com.remswork.project.alice.model.Subject;

import java.util.List;

public interface SubjectDao {

    Subject getSubjectById(long id) throws SubjectException;
    List<Subject> getSubjectList() throws SubjectException;
    Subject addSubject(Subject subject) throws SubjectException;
    Subject updateSubjectById(long id, Subject newSubject) throws SubjectException;
    Subject deleteSubjectById(long id) throws SubjectException;

}
