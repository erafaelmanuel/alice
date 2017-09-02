package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.ProjectDao;
import com.remswork.project.alice.dao.exception.GradingFactorDaoException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Project;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.model.Term;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectDaoImpl implements ProjectDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Project getProjectById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Project project = session.get(Project.class, id);
            if(project == null)
                throw new GradingFactorDaoException("Project with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return project;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Project> getProjectList() throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Project> projectList = new ArrayList<>();
            Query query = session.createQuery("from Project");
            for (Object objProject : query.list())
                projectList.add((Project) objProject);
            session.getTransaction().commit();
            session.close();
            return projectList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Project> getProjectListByStudentAndSubjectId(long studentId, long subjectId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Project> projectList = new ArrayList<>();
            Query query =
                    session.createQuery("from Project where student.id = :studentId and subject.id = :subjectId");
            query.setParameter("studentId", studentId);
            query.setParameter("subjectId", subjectId);
            for (Object objProject : query.list())
                projectList.add((Project) objProject);
            session.getTransaction().commit();
            session.close();
            return projectList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public List<Project> getProjectListByStudentAndSubjectId(long studentId, long subjectId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Project> projectList = new ArrayList<>();
            String hql =
                    "from Project where student.id = :studentId and subject.id = :subjectId and term.id = :termId";
            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            query.setParameter("subjectId", subjectId);
            query.setParameter("termId", termId);
            for (Object objProject : query.list())
                projectList.add((Project) objProject);
            session.getTransaction().commit();
            session.close();
            return projectList;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Project addProject(Project project, long studentId, long subjectId) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, subjectId);

            if (project == null)
                throw new GradingFactorException("You tried to add class with a null value");
            if (studentId == 0)
                throw new GradingFactorException("Query param : studentId is required");
            if (subjectId == 0)
                throw new GradingFactorException("Query param : subjectId is required");
            if (student == null)
                throw new GradingFactorException("Project's student with id : " + studentId + " does not exist");
            if (subject == null)
                throw new GradingFactorException("Project's subject with id : " + subjectId + " does not exist");
            if (project.getTitle() == null)
                throw new GradingFactorException("Project's title is required");
            if (project.getTitle().trim().equals(""))
                throw new GradingFactorException("Project can't have an empty title");
            if (project.getDate() == null)
                throw new GradingFactorException("Project's date is required");
            if (project.getDate().trim().equals(""))
                throw new GradingFactorException("Project can't have an empty date");
            if(project.getItemTotal() < 0)
                throw new GradingFactorException("Project's itemTotal is invalid");
            if(project.getScore() < 0 && project.getScore() > project.getItemTotal())
                throw new GradingFactorException("Project's score is invalid");

            project.setStudent(student);
            project.setSubject(subject);

            session.persist(project);
            session.getTransaction().commit();
            session.close();
            return project;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Project addProject(Project project, long studentId, long subjectId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, subjectId);
            Term term = session.get(Term.class, termId);

            if (project == null)
                throw new GradingFactorException("You tried to add class with a null value");
            if (studentId == 0)
                throw new GradingFactorException("Query param : studentId is required");
            if (subjectId == 0)
                throw new GradingFactorException("Query param : subjectId is required");
            if (termId < 1)
                throw new GradingFactorException("Query param : termId has in invalid");
            if (student == null)
                throw new GradingFactorException("Project's student with id : " + studentId + " does not exist");
            if (subject == null)
                throw new GradingFactorException("Project's subject with id : " + subjectId + " does not exist");
            if (term == null)
                throw new GradingFactorException("Project's term with id : " + termId + " does not exist");
            if (project.getTitle() == null)
                throw new GradingFactorException("Project's title is required");
            if (project.getTitle().trim().equals(""))
                throw new GradingFactorException("Project can't have an empty title");
            if (project.getDate() == null)
                throw new GradingFactorException("Project's date is required");
            if (project.getDate().trim().equals(""))
                throw new GradingFactorException("Project can't have an empty date");
            if(project.getItemTotal() < 0)
                throw new GradingFactorException("Project's itemTotal is invalid");
            if(project.getScore() < 0 && project.getScore() > project.getItemTotal())
                throw new GradingFactorException("Project's score is invalid");

            project.setStudent(student);
            project.setSubject(subject);
            project.setTerm(term);

            session.persist(project);
            session.getTransaction().commit();
            session.close();
            return project;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Project updateProjectById(long id, Project newProject, long studentId, long subjectId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Project project = session.get(Project.class, id);
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, subjectId);

            if(newProject == null)
                newProject = new Project();
            if(project == null)
                throw new GradingFactorException("Project with id : " + id + " does not exist");
            if (student == null && studentId != 0)
                throw new GradingFactorException("Project's student with id : " + studentId + " does not exist");
            if (subject == null && subjectId != 0)
                throw new GradingFactorException("Project's subject with id : " + subjectId + " does not exist");
            if(!(newProject.getTitle() != null ? newProject.getTitle() : "").trim().isEmpty())
                project.setTitle(newProject.getTitle());
            if(!(newProject.getDate() != null ? newProject.getDate() : "").trim().isEmpty())
                project.setDate(newProject.getDate());
            if(studentId > 0) {
                if(studentId == (project.getStudent() != null ? project.getStudent().getId() : 0))
                    throw new GradingFactorException("Project's  student with id : " + id + " already exist");
                project.setStudent(student);
            }
            if(subjectId > 0) {
                if(subjectId == (project.getSubject() != null ? project.getSubject().getId() : 0))
                    throw new GradingFactorException("Project's  student with id : " + id + " already exist");
                project.setSubject(subject);
            }
            session.getTransaction().commit();
            session.close();
            return project;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Project updateProjectById(long id, Project newProject, long studentId, long subjectId, long termId)
            throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Project project = session.get(Project.class, id);
            Student student = session.get(Student.class, studentId);
            Subject subject = session.get(Subject.class, subjectId);
            Term term = session.get(Term.class, termId);

            if(newProject == null)
                newProject = new Project();
            if(project == null)
                throw new GradingFactorException("Project with id : " + id + " does not exist");
            if (student == null && studentId != 0)
                throw new GradingFactorException("Project's student with id : " + studentId + " does not exist");
            if (subject == null && subjectId != 0)
                throw new GradingFactorException("Project's subject with id : " + subjectId + " does not exist");
            if (term == null && termId > 0)
                throw new GradingFactorException("Project's term with id : " + termId + " does not exist");
            if(!(newProject.getTitle() != null ? newProject.getTitle() : "").trim().isEmpty())
                project.setTitle(newProject.getTitle());
            if(!(newProject.getDate() != null ? newProject.getDate() : "").trim().isEmpty())
                project.setDate(newProject.getDate());
            if(studentId > 0) {
                if(studentId == (project.getStudent() != null ? project.getStudent().getId() : 0))
                    throw new GradingFactorException("Project's  student with id : " + id + " already exist");
                project.setStudent(student);
            }
            if(subjectId > 0) {
                if(subjectId == (project.getSubject() != null ? project.getSubject().getId() : 0))
                    throw new GradingFactorException("Project's  student with id : " + id + " already exist");
                project.setSubject(subject);
            }
            if(termId > 0) {
                if(termId != (project.getTerm() != null ? project.getTerm().getId() : 0))
                    project.setTerm(term);
            }
            session.getTransaction().commit();
            session.close();
            return project;
        } catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }

    @Override
    public Project deleteProjectById(long id) throws GradingFactorException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Project project = session.get(Project.class, id);
            if(project == null)
                throw new GradingFactorDaoException("Project with id : " + id + " does not exist");
            session.delete(project);
            session.getTransaction().commit();
            session.close();
            return project;
        }catch (GradingFactorDaoException e) {
            session.close();
            throw new GradingFactorException(e.getMessage());
        }
    }
}
