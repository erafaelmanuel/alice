package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.SubjectDaoImpl;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectDaoImpl subjectDao;

    @Override
    public Subject getSubjectById(long id) {
        return subjectDao.getSubjectById(id);
    }

    @Override
    public List<Subject> getSubjectList() {
        return subjectDao.getSubjectList();
    }

    @Override
    public Subject addSubject(Subject subject) {
        return subjectDao.addSubject(subject);
    }

    @Override
    public Subject updateSubjectById(long id, Subject newSubject) {
        return subjectDao.updateSubjectById(id, newSubject);
    }

    @Override
    public Subject deleteSubjectById(long id) {
        return subjectDao.deleteSubjectById(id);
    }
}
