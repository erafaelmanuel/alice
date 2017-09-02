package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Project;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.ProjectResourceLinks;
import com.remswork.project.alice.resource.links.StudentResourceLinks;
import com.remswork.project.alice.resource.links.SubjectResourceLinks;
import com.remswork.project.alice.service.impl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("project")
public class ProjectResource {

    @Autowired
    private ProjectServiceImpl projectService;
    @Context
    private UriInfo uriInfo;
    @QueryParam("studentId")
    private long studentId;
    @QueryParam("subjectId")
    private long subjectId;
    @QueryParam("termId")
    private long termId;

    @GET
    @Path("{projectId}")
    public Response getProjectById(@PathParam("projectId") long id) {
        try {
            ProjectResourceLinks resourceLinks = new ProjectResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            Project project = projectService.getProjectById(id);
            project.addLink(resourceLinks.self(id));
            if(project.getStudent() != null)
                project.getStudent().addLink(studentResourceLinks.self(project.getStudent().getId()));
            if(project.getSubject() != null)
                project.getSubject().addLink(subjectResourceLinks.self(project.getSubject().getId()));
            return Response.status(Response.Status.OK).entity(project).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getProjectList() {
        try {
            List<Project> projectList;
            ProjectResourceLinks resourceLinks = new ProjectResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            if(studentId != 0 && subjectId != 0 && termId != 0)
                projectList = projectService.getProjectListByStudentAndSubjectId(studentId, subjectId, termId);
            else if(studentId != 0 && subjectId != 0)
                projectList = projectService.getProjectListByStudentAndSubjectId(studentId, subjectId);
            else
                projectList = projectService.getProjectList();
            for(Project project : projectList) {
                project.addLink(resourceLinks.self(project.getId()));
                if(project.getStudent() != null)
                    project.getStudent().addLink(studentResourceLinks.self(project.getStudent().getId()));
                if(project.getSubject() != null)
                    project.getSubject().addLink(subjectResourceLinks.self(project.getSubject().getId()));
            }
            GenericEntity<List<Project>> entity = new GenericEntity<List<Project>>(projectList){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addProject(Project project) {
        try {
            ProjectResourceLinks resourceLinks = new ProjectResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            if(termId > 0)
                project = projectService.addProject(project, studentId, subjectId, termId);
            else
                project = projectService.addProject(project, studentId, subjectId);
            project.addLink(resourceLinks.self(project.getId()));
            if(project.getStudent() != null)
                project.getStudent().addLink(studentResourceLinks.self(project.getStudent().getId()));
            if(project.getSubject() != null)
                project.getSubject().addLink(subjectResourceLinks.self(project.getSubject().getId()));
            return Response.status(Response.Status.CREATED).entity(project).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{projectId}")
    public Response updateProjectById(@PathParam("projectId") long id, Project newProject) {
        try {
            ProjectResourceLinks resourceLinks = new ProjectResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);
            Project project;

            if(termId > 0)
                project = projectService.updateProjectById(id, newProject, studentId, subjectId, termId);
            else
                project = projectService.updateProjectById(id, newProject, studentId, subjectId);
            project.addLink(resourceLinks.self(project.getId()));
            if(project.getStudent() != null)
                project.getStudent().addLink(studentResourceLinks.self(project.getStudent().getId()));
            if(project.getSubject() != null)
                project.getSubject().addLink(subjectResourceLinks.self(project.getSubject().getId()));
            return Response.status(Response.Status.OK).entity(project).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{projectId}")
    public Response deleteProjectById(@PathParam("projectId") long id) {
        try {
            ProjectResourceLinks resourceLinks = new ProjectResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            Project project = projectService.deleteProjectById(id);
            project.addLink(resourceLinks.self(project.getId()));
            if(project.getStudent() != null)
                project.getStudent().addLink(studentResourceLinks.self(project.getStudent().getId()));
            if(project.getSubject() != null)
                project.getSubject().addLink(subjectResourceLinks.self(project.getSubject().getId()));
            return Response.status(Response.Status.OK).entity(project).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }
}
