package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Assignment;

import java.util.List;

public interface AssignmentDao {

    Assignment getAssignmentById(long id) throws GradingFactorException;

    List<Assignment> getAssignmentList() throws GradingFactorException;

    List<Assignment> getAssignmentListByStudentAndSubjectId(long studentId, long subjectId)
            throws GradingFactorException;

    Assignment addAssignment(Assignment assignment, long studentId, long subjectId) throws GradingFactorException;

    Assignment updateAssignmentById(long id, Assignment newAssignment, long studentId, long subjectId)
            throws GradingFactorException;

    Assignment deleteAssignmentById(long id) throws GradingFactorException;
}
