package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.SubjectDaoImpl;
import com.remswork.project.alice.exception.SubjectException;
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
    public Subject getSubjectById(long id) throws SubjectException {
        Subject subject = subjectDao.getSubjectById(id);
        if(subject == null)
            throw new SubjectException("No subject to return");
        return subject;
    }

    @Override
    public List<Subject> getSubjectList() throws SubjectException {
        List<Subject> subjectList = subjectDao.getSubjectList();
        if(subjectList == null)
            throw new SubjectException("No subject to return");
        return subjectList;
    }

    @Override
    public Subject addSubject(Subject subject) throws SubjectException {
        Subject s = subjectDao.addSubject(subject);
        if(s == null)
            throw new SubjectException("No subject to return");
        return s;
    }

    @Override
    public Subject updateSubjectById(long id, Subject newSubject) throws SubjectException {
        Subject subject = subjectDao.updateSubjectById(id, newSubject);
        if(subject == null)
            throw new SubjectException("No subject to return");
        return subject;
    }

    @Override
    public Subject deleteSubjectById(long id) throws SubjectException {
        Subject subject = subjectDao.deleteSubjectById(id);
        if(subject == null)
            throw new SubjectException("No subject to return");
        return subject;
    }
}
