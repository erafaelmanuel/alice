package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Exam;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.ExamResourceLinks;
import com.remswork.project.alice.resource.links.StudentResourceLinks;
import com.remswork.project.alice.resource.links.SubjectResourceLinks;
import com.remswork.project.alice.service.impl.ExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("exam")
public class ExamResource {

    @Autowired
    private ExamServiceImpl examService;
    @Context
    private UriInfo uriInfo;
    @QueryParam("studentId")
    private long studentId;
    @QueryParam("subjectId")
    private long subjectId;

    @GET
    @Path("{examId}")
    public Response getExamById(@PathParam("examId") long id) {
        try {
            ExamResourceLinks resourceLinks = new ExamResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            Exam exam = examService.getExamById(id);
            exam.addLink(resourceLinks.self(id));
            if(exam.getStudent() != null)
                exam.getStudent().addLink(studentResourceLinks.self(exam.getStudent().getId()));
            if(exam.getSubject() != null)
                exam.getSubject().addLink(subjectResourceLinks.self(exam.getSubject().getId()));
            return Response.status(Response.Status.OK).entity(exam).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getExamList() {
        try {
            List<Exam> examList;
            ExamResourceLinks resourceLinks = new ExamResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            if(studentId != 0 || subjectId != 0)
                examList = examService.getExamListByStudentAndSubjectId(studentId, subjectId);
            else
                examList = examService.getExamList();
            for(Exam exam : examList) {
                exam.addLink(resourceLinks.self(exam.getId()));
                if(exam.getStudent() != null)
                    exam.getStudent().addLink(studentResourceLinks.self(exam.getStudent().getId()));
                if(exam.getSubject() != null)
                    exam.getSubject().addLink(subjectResourceLinks.self(exam.getSubject().getId()));
            }
            GenericEntity<List<Exam>> entity = new GenericEntity<List<Exam>>(examList){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addExam(Exam exam) {
        try {
            ExamResourceLinks resourceLinks = new ExamResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            exam = examService.addExam(exam, studentId, subjectId);
            exam.addLink(resourceLinks.self(exam.getId()));
            if(exam.getStudent() != null)
                exam.getStudent().addLink(studentResourceLinks.self(exam.getStudent().getId()));
            if(exam.getSubject() != null)
                exam.getSubject().addLink(subjectResourceLinks.self(exam.getSubject().getId()));
            return Response.status(Response.Status.CREATED).entity(exam).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{examId}")
    public Response updateExamById(@PathParam("examId") long id, Exam newExam) {
        try {
            ExamResourceLinks resourceLinks = new ExamResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            Exam exam = examService.updateExamById(id, newExam, studentId, subjectId);
            exam.addLink(resourceLinks.self(exam.getId()));
            if(exam.getStudent() != null)
                exam.getStudent().addLink(studentResourceLinks.self(exam.getStudent().getId()));
            if(exam.getSubject() != null)
                exam.getSubject().addLink(subjectResourceLinks.self(exam.getSubject().getId()));
            return Response.status(Response.Status.OK).entity(exam).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{examId}")
    public Response deleteExamById(@PathParam("examId") long id) {
        try {
            ExamResourceLinks resourceLinks = new ExamResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            Exam exam = examService.deleteExamById(id);
            exam.addLink(resourceLinks.self(exam.getId()));
            if(exam.getStudent() != null)
                exam.getStudent().addLink(studentResourceLinks.self(exam.getStudent().getId()));
            if(exam.getSubject() != null)
                exam.getSubject().addLink(subjectResourceLinks.self(exam.getSubject().getId()));
            return Response.status(Response.Status.OK).entity(exam).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }
}
