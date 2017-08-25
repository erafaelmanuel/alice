package com.remswork.project.alice.service;

import com.remswork.project.alice.model.Subject;

import java.util.List;

public interface SubjectService {

    Subject getSubjectById(long id);
    List<Subject> getSubjectList();
    Subject addSubject(Subject subject);
    Subject updateSubjectById(long id, Subject newSubject);
    Subject deleteSubjectById(long id);
}
