package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Attendance;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.AttendanceResourceLinks;
import com.remswork.project.alice.resource.links.StudentResourceLinks;
import com.remswork.project.alice.resource.links.SubjectResourceLinks;
import com.remswork.project.alice.service.impl.AttendanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("attendance")
public class AttendanceResource {

    @Autowired
    private AttendanceServiceImpl attendanceService;
    @Context
    private UriInfo uriInfo;
    @QueryParam("studentId")
    private long studentId;
    @QueryParam("subjectId")
    private long subjectId;

    @GET
    @Path("{attendanceId}")
    public Response getAttendanceById(@PathParam("attendanceId") long id) {
        try {
            AttendanceResourceLinks resourceLinks = new AttendanceResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            Attendance attendance = attendanceService.getAttendanceById(id);
            attendance.addLink(resourceLinks.self(id));
            if(attendance.getStudent() != null)
                attendance.getStudent().addLink(studentResourceLinks.self(attendance.getStudent().getId()));
            if(attendance.getSubject() != null)
                attendance.getSubject().addLink(subjectResourceLinks.self(attendance.getSubject().getId()));
            return Response.status(Response.Status.OK).entity(attendance).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getAttendanceList() {
        try {
            List<Attendance> attendanceList;
            AttendanceResourceLinks resourceLinks = new AttendanceResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            if(studentId != 0 || subjectId != 0)
                attendanceList = attendanceService.getAttendanceListByStudentAndSubjectId(studentId, subjectId);
            else
                attendanceList = attendanceService.getAttendanceList();
            for(Attendance attendance : attendanceList) {
                attendance.addLink(resourceLinks.self(attendance.getId()));
                if(attendance.getStudent() != null)
                    attendance.getStudent().addLink(studentResourceLinks.self(attendance.getStudent().getId()));
                if(attendance.getSubject() != null)
                    attendance.getSubject().addLink(subjectResourceLinks.self(attendance.getSubject().getId()));
            }
            GenericEntity<List<Attendance>> entity = new GenericEntity<List<Attendance>>(attendanceList){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addAttendance(Attendance attendance) {
        try {
            AttendanceResourceLinks resourceLinks = new AttendanceResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            attendance = attendanceService.addAttendance(attendance, studentId, subjectId);
            attendance.addLink(resourceLinks.self(attendance.getId()));
            if(attendance.getStudent() != null)
                attendance.getStudent().addLink(studentResourceLinks.self(attendance.getStudent().getId()));
            if(attendance.getSubject() != null)
                attendance.getSubject().addLink(subjectResourceLinks.self(attendance.getSubject().getId()));
            return Response.status(Response.Status.CREATED).entity(attendance).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{attendanceId}")
    public Response updateAttendanceById(@PathParam("attendanceId") long id, Attendance newAttendance) {
        try {
            AttendanceResourceLinks resourceLinks = new AttendanceResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            Attendance attendance = attendanceService.updateAttendanceById(id, newAttendance, studentId, subjectId);
            attendance.addLink(resourceLinks.self(attendance.getId()));
            if(attendance.getStudent() != null)
                attendance.getStudent().addLink(studentResourceLinks.self(attendance.getStudent().getId()));
            if(attendance.getSubject() != null)
                attendance.getSubject().addLink(subjectResourceLinks.self(attendance.getSubject().getId()));
            return Response.status(Response.Status.OK).entity(attendance).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{attendanceId}")
    public Response deleteAttendanceById(@PathParam("attendanceId") long id) {
        try {
            AttendanceResourceLinks resourceLinks = new AttendanceResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            Attendance attendance = attendanceService.deleteAttendanceById(id);
            attendance.addLink(resourceLinks.self(attendance.getId()));
            if(attendance.getStudent() != null)
                attendance.getStudent().addLink(studentResourceLinks.self(attendance.getStudent().getId()));
            if(attendance.getSubject() != null)
                attendance.getSubject().addLink(subjectResourceLinks.self(attendance.getSubject().getId()));
            return Response.status(Response.Status.OK).entity(attendance).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }
}
