package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.ExamDao;
import com.remswork.project.alice.dao.impl.ExamDaoImpl;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceImpl implements ExamDao {

    @Autowired
    private ExamDaoImpl examDao;


    @Override
    public Exam getExamById(long id) throws GradingFactorException {
        return examDao.getExamById(id);
    }

    @Override
    public List<Exam> getExamList() throws GradingFactorException {
        return examDao.getExamList();
    }

    @Override
    public List<Exam> getExamListByStudentAndSubjectId(long studentId, long subjectId)
            throws GradingFactorException {
        return examDao.getExamListByStudentAndSubjectId(studentId, subjectId);
    }

    @Override
    public Exam addExam(Exam exam, long studentId, long subjectId) throws GradingFactorException {
        return examDao.addExam(exam, studentId, subjectId);
    }

    @Override
    public Exam updateExamById(long id, Exam newExam, long studentId, long subjectId)
            throws GradingFactorException {
        return examDao.updateExamById(id, newExam, studentId, subjectId);
    }

    @Override
    public Exam deleteExamById(long id) throws GradingFactorException {
        return examDao.deleteExamById(id);
    }
}