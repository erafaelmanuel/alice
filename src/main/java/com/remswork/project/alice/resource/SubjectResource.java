package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.SubjectException;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.SubjectResourceLinks;
import com.remswork.project.alice.service.impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("subject")
public class SubjectResource {

    @Autowired
    private SubjectServiceImpl subjectService;
    @Context
    private UriInfo uriInfo;

    @GET
    @Path("{subjectId}")
    public Response getSubjectById(@PathParam("subjectId") long id) {
        try {
            SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);
            Subject subject = subjectService.getSubjectById(id);
            subject.addLink(resourceLinks.self(id));
            return Response
                    .status(Response.Status.OK)
                    .entity(subject)
                    .build();
        }catch (SubjectException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found");
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getSubjectList() {
        try {
            SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);
            List<Subject> subjectList = subjectService.getSubjectList();
            for(Subject subject : subjectList)
                subject.addLink(resourceLinks.self(subject.getId()));
            GenericEntity<List<Subject>> entity = new GenericEntity<List<Subject>>(subjectList){};
            return Response
                    .status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }catch (SubjectException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found");
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addSubject(Subject subject) {
        try {
            SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);
            subject = subjectService.addSubject(subject);
            subject.addLink(resourceLinks.self(subject.getId()));
            return Response
                    .status(Response.Status.OK)
                    .entity(subject)
                    .build();
        }catch (SubjectException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request");
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{subjectId}")
    public Response updateSubjectById(@PathParam("subjectId") long id, Subject newSubject) {
        try {
            SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);
            Subject subject = subjectService.updateSubjectById(id, newSubject);
            subject.addLink(resourceLinks.self(subject.getId()));
            return Response
                    .status(Response.Status.OK)
                    .entity(subject)
                    .build();
        }catch (SubjectException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request");
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{subjectId}")
    public Response deleteSubjectById(@PathParam("subjectUd") long id) {
        try {
            SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);
            Subject subject = subjectService.deleteSubjectById(id);
            subject.addLink(resourceLinks.self(subject.getId()));
            return Response
                    .status(Response.Status.OK)
                    .entity(subject)
                    .build();
        }catch (SubjectException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request");
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

}