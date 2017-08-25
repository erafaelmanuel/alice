package com.remswork.project.alice.resource;

import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.resource.exception.SubjectResourceException;
import com.remswork.project.alice.resource.links.SubjectResourceLinks;
import com.remswork.project.alice.service.impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
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
            if(subject == null)
                throw new SubjectResourceException("No SubjectResource to display");
            subject.addLink(resourceLinks.self(id));
            return Response
                    .status(Response.Status.OK)
                    .entity(subject)
                    .build();
        }catch (SubjectResourceException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    public Response getSubjectList() {
        try {
            SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);
            List<Subject> subjectList = subjectService.getSubjectList();
            if(subjectList.size() < 1)
                throw new SubjectResourceException("No SubjectResource to display");
            for(Subject subject : subjectList)
                subject.addLink(resourceLinks.self(subject.getId()));
            GenericEntity<List<Subject>> entity = new GenericEntity<List<Subject>>(subjectList){};
            return Response
                    .status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }catch (SubjectResourceException e) {
            e.printStackTrace();
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(new GenericEntity<List<Subject>>(new ArrayList<>()){})
                    .build();
        }
    }

    @POST
    public Response addSubject(Subject subject) {
        try {
            SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);
            subject = subjectService.addSubject(subject);
            if(subject == null)
                throw new SubjectResourceException("No SubjectResource to display");
            subject.addLink(resourceLinks.self(subject.getId()));
            return Response
                    .status(Response.Status.OK)
                    .entity(subject)
                    .build();
        }catch (SubjectResourceException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("{subjectId}")
    public Response updateSubjectById(@PathParam("subjectId") long id, Subject newSubject) {
        try {
            SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);
            Subject subject = subjectService.updateSubjectById(id, newSubject);
            if(subject == null)
                throw new SubjectResourceException("No SubjectResource to display");
            subject.addLink(resourceLinks.self(subject.getId()));
            return Response
                    .status(Response.Status.OK)
                    .entity(subject)
                    .build();
        }catch (SubjectResourceException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("{subjectId}")
    public Response deleteSubjectById(@PathParam("subjectUd") long id) {
        try {
            SubjectResourceLinks resourceLinks = new SubjectResourceLinks(uriInfo);
            Subject subject = subjectService.deleteSubjectById(id);
            if(subject == null)
                throw new SubjectResourceException("No SubjectResource to display");
            subject.addLink(resourceLinks.self(subject.getId()));
            return Response
                    .status(Response.Status.OK)
                    .entity(subject)
                    .build();
        }catch (SubjectResourceException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
