package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.RecitationDaoImpl;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Recitation;
import com.remswork.project.alice.service.RecitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecitationServiceImpl implements RecitationService {

    @Autowired
    private RecitationDaoImpl recitationDao;


    @Override
    public Recitation getRecitationById(long id) throws GradingFactorException {
        return recitationDao.getRecitationById(id);
    }

    @Override
    public List<Recitation> getRecitationList() throws GradingFactorException {
        return recitationDao.getRecitationList();
    }

    @Override
    public List<Recitation> getRecitationListByStudentAndSubjectId(long studentId, long subjectId)
            throws GradingFactorException {
        return recitationDao.getRecitationListByStudentAndSubjectId(studentId, subjectId);
    }

    @Override
    public List<Recitation> getRecitationListByStudentAndSubjectId(long studentId, long subjectId, long termId)
            throws GradingFactorException {
        return recitationDao.getRecitationListByStudentAndSubjectId(studentId, subjectId, termId);
    }

    @Override
    public Recitation addRecitation(Recitation recitation, long studentId, long subjectId)
            throws GradingFactorException {
        return recitationDao.addRecitation(recitation, studentId, subjectId);
    }

    @Override
    public Recitation addRecitation(Recitation recitation, long studentId, long subjectId, long termId)
            throws GradingFactorException {
        return recitationDao.addRecitation(recitation, studentId, subjectId, termId);
    }

    @Override
    public Recitation updateRecitationById(long id, Recitation newRecitation, long studentId, long subjectId)
            throws GradingFactorException {
        return recitationDao.updateRecitationById(id, newRecitation, studentId, subjectId);
    }

    @Override
    public Recitation updateRecitationById(long id, Recitation newRecitation, long studentId, long subjectId,
                                           long termId) throws GradingFactorException {
        return recitationDao.updateRecitationById(id, newRecitation, studentId, subjectId, termId);
    }

    @Override
    public Recitation deleteRecitationById(long id) throws GradingFactorException {
        return recitationDao.deleteRecitationById(id);
    }
}