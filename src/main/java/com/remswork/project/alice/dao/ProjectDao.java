package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Project;

import java.util.List;

public interface ProjectDao {

    Project getProjectById(long id) throws GradingFactorException;

    List<Project> getProjectList() throws GradingFactorException;

    List<Project> getProjectListByStudentAndSubjectId(long studentId, long subjectId) throws GradingFactorException;

    List<Project> getProjectListByStudentAndSubjectId(long studentId, long subjectId, long termId)
            throws GradingFactorException;

    Project addProject(Project project, long studentId, long subjectId) throws GradingFactorException;

    Project addProject(Project project, long studentId, long subjectId, long termId)
            throws GradingFactorException;

    Project updateProjectById(long id, Project newProject, long studentId, long subjectId)
            throws GradingFactorException;

    Project updateProjectById(long id, Project newProject, long studentId, long subjectId, long termId)
            throws GradingFactorException;

    Project deleteProjectById(long id) throws GradingFactorException;
}
