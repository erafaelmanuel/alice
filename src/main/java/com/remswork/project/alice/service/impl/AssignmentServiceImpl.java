package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.AssignmentDaoImpl;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Assignment;
import com.remswork.project.alice.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentDaoImpl assignmentDao;


    @Override
    public Assignment getAssignmentById(long id) throws GradingFactorException {
        return assignmentDao.getAssignmentById(id);
    }

    @Override
    public List<Assignment> getAssignmentList() throws GradingFactorException {
        return assignmentDao.getAssignmentList();
    }

    @Override
    public List<Assignment> getAssignmentListByStudentAndSubjectId(long studentId, long subjectId)
            throws GradingFactorException {
        return assignmentDao.getAssignmentListByStudentAndSubjectId(studentId, subjectId);
    }

    @Override
    public List<Assignment> getAssignmentListByStudentAndSubjectId(long studentId, long subjectId, long termId)
            throws GradingFactorException {
        return assignmentDao.getAssignmentListByStudentAndSubjectId(studentId, subjectId, termId);
    }

    @Override
    public Assignment addAssignment(Assignment assignment, long studentId, long subjectId) throws GradingFactorException {
        return assignmentDao.addAssignment(assignment, studentId, subjectId);
    }

    @Override
    public Assignment addAssignment(Assignment assignment, long studentId, long subjectId, long termId)
            throws GradingFactorException {
        return assignmentDao.addAssignment(assignment, studentId, subjectId, termId);
    }

    @Override
    public Assignment updateAssignmentById(long id, Assignment newAssignment, long studentId, long subjectId)
            throws GradingFactorException {
        return assignmentDao.updateAssignmentById(id, newAssignment, studentId, subjectId);
    }

    @Override
    public Assignment updateAssignmentById(long id, Assignment newAssignment, long studentId, long subjectId,
                                           long termId) throws GradingFactorException {
        return assignmentDao.updateAssignmentById(id, newAssignment, studentId, subjectId, termId);
    }

    @Override
    public Assignment deleteAssignmentById(long id) throws GradingFactorException {
        return assignmentDao.deleteAssignmentById(id);
    }
}