package com.remswork.project.alice.service;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Recitation;

import java.util.List;

public interface RecitationService {

    Recitation getRecitationById(long id) throws GradingFactorException;

    List<Recitation> getRecitationList() throws GradingFactorException;

    List<Recitation> getRecitationListByStudentAndSubjectId(long studentId, long subjectId)
            throws GradingFactorException;

    Recitation addRecitation(Recitation recitation, long studentId, long subjectId) throws GradingFactorException;

    Recitation updateRecitationById(long id, Recitation newRecitation, long studentId, long subjectId)
            throws GradingFactorException;

    Recitation deleteRecitationById(long id) throws GradingFactorException;
}
