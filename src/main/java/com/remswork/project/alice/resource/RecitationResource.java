package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Recitation;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.RecitationResourceLinks;
import com.remswork.project.alice.resource.links.StudentResourceLinks;
import com.remswork.project.alice.resource.links.SubjectResourceLinks;
import com.remswork.project.alice.service.impl.RecitationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("recitation")
public class RecitationResource {

    @Autowired
    private RecitationServiceImpl recitationService;
    @Context
    private UriInfo uriInfo;
    @QueryParam("studentId")
    private long studentId;
    @QueryParam("subjectId")
    private long subjectId;
    @QueryParam("termId")
    private long termId;

    @GET
    @Path("{recitationId}")
    public Response getRecitationById(@PathParam("recitationId") long id) {
        try {
            RecitationResourceLinks resourceLinks = new RecitationResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            Recitation recitation = recitationService.getRecitationById(id);
            recitation.addLink(resourceLinks.self(id));
            if(recitation.getStudent() != null)
                recitation.getStudent().addLink(studentResourceLinks.self(recitation.getStudent().getId()));
            if(recitation.getSubject() != null)
                recitation.getSubject().addLink(subjectResourceLinks.self(recitation.getSubject().getId()));
            return Response.status(Response.Status.OK).entity(recitation).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getRecitationList() {
        try {
            List<Recitation> recitationList;
            RecitationResourceLinks resourceLinks = new RecitationResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            if(studentId != 0 && subjectId != 0 && termId != 0)
                recitationList = recitationService.getRecitationListByStudentAndSubjectId(studentId, subjectId, termId);
            else if(studentId != 0 && subjectId != 0)
                recitationList = recitationService.getRecitationListByStudentAndSubjectId(studentId, subjectId);
            else
                recitationList = recitationService.getRecitationList();
            for(Recitation recitation : recitationList) {
                recitation.addLink(resourceLinks.self(recitation.getId()));
                if(recitation.getStudent() != null)
                    recitation.getStudent().addLink(studentResourceLinks.self(recitation.getStudent().getId()));
                if(recitation.getSubject() != null)
                    recitation.getSubject().addLink(subjectResourceLinks.self(recitation.getSubject().getId()));
            }
            GenericEntity<List<Recitation>> entity = new GenericEntity<List<Recitation>>(recitationList){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addRecitation(Recitation recitation) {
        try {
            RecitationResourceLinks resourceLinks = new RecitationResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            if(termId > 0)
                recitation = recitationService.addRecitation(recitation, studentId, subjectId, termId);
            else
                recitation = recitationService.addRecitation(recitation, studentId, subjectId);
            recitation.addLink(resourceLinks.self(recitation.getId()));
            if(recitation.getStudent() != null)
                recitation.getStudent().addLink(studentResourceLinks.self(recitation.getStudent().getId()));
            if(recitation.getSubject() != null)
                recitation.getSubject().addLink(subjectResourceLinks.self(recitation.getSubject().getId()));
            return Response.status(Response.Status.CREATED).entity(recitation).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{recitationId}")
    public Response updateRecitationById(@PathParam("recitationId") long id, Recitation newRecitation) {
        try {
            RecitationResourceLinks resourceLinks = new RecitationResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);
            Recitation recitation;

            if(termId > 0)
                recitation = recitationService.updateRecitationById(id, newRecitation, studentId, subjectId, termId);
            else
                recitation = recitationService.updateRecitationById(id, newRecitation, studentId, subjectId);
            recitation.addLink(resourceLinks.self(recitation.getId()));
            if(recitation.getStudent() != null)
                recitation.getStudent().addLink(studentResourceLinks.self(recitation.getStudent().getId()));
            if(recitation.getSubject() != null)
                recitation.getSubject().addLink(subjectResourceLinks.self(recitation.getSubject().getId()));
            return Response.status(Response.Status.OK).entity(recitation).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{recitationId}")
    public Response deleteRecitationById(@PathParam("recitationId") long id) {
        try {
            RecitationResourceLinks resourceLinks = new RecitationResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            Recitation recitation = recitationService.deleteRecitationById(id);
            recitation.addLink(resourceLinks.self(recitation.getId()));
            if(recitation.getStudent() != null)
                recitation.getStudent().addLink(studentResourceLinks.self(recitation.getStudent().getId()));
            if(recitation.getSubject() != null)
                recitation.getSubject().addLink(subjectResourceLinks.self(recitation.getSubject().getId()));
            return Response.status(Response.Status.OK).entity(recitation).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }
}
