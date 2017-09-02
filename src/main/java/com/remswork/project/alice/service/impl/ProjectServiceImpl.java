package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.ProjectDaoImpl;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Project;
import com.remswork.project.alice.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDaoImpl projectDao;


    @Override
    public Project getProjectById(long id) throws GradingFactorException {
        return projectDao.getProjectById(id);
    }

    @Override
    public List<Project> getProjectList() throws GradingFactorException {
        return projectDao.getProjectList();
    }

    @Override
    public List<Project> getProjectListByStudentAndSubjectId(long studentId, long subjectId)
            throws GradingFactorException {
        return projectDao.getProjectListByStudentAndSubjectId(studentId, subjectId);
    }

    @Override
    public List<Project> getProjectListByStudentAndSubjectId(long studentId, long subjectId, long termId)
            throws GradingFactorException {
        return projectDao.getProjectListByStudentAndSubjectId(studentId, subjectId, termId);
    }

    @Override
    public Project addProject(Project project, long studentId, long subjectId) throws GradingFactorException {
        return projectDao.addProject(project, studentId, subjectId);
    }

    @Override
    public Project addProject(Project project, long studentId, long subjectId, long termId)
            throws GradingFactorException {
        return projectDao.addProject(project, studentId, subjectId, termId);
    }

    @Override
    public Project updateProjectById(long id, Project newProject, long studentId, long subjectId)
            throws GradingFactorException {
        return projectDao.updateProjectById(id, newProject, studentId, subjectId);
    }

    @Override
    public Project updateProjectById(long id, Project newProject, long studentId, long subjectId, long termId)
            throws GradingFactorException {
        return projectDao.updateProjectById(id, newProject, studentId, subjectId, termId);
    }

    @Override
    public Project deleteProjectById(long id) throws GradingFactorException {
        return projectDao.deleteProjectById(id);
    }
}