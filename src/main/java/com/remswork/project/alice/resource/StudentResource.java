package com.remswork.project.alice.resource;

import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.resource.exception.StudentResourceException;
import com.remswork.project.alice.resource.links.StudentResourceLinks;
import com.remswork.project.alice.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("student")
public class StudentResource {

    @Autowired
    private StudentServiceImpl studentService;
    @Context
    private UriInfo uriInfo;

    @GET
    @Path("{studentId}")
    public Response getStudentById(@PathParam("studentId") long id) {
       try{
           Student student = studentService.getStudentById(id);
           StudentResourceLinks resourceLinks = new StudentResourceLinks(uriInfo);
           if(student == null)
               throw new StudentResourceException("No StudentResource to display");
           student.addLink(resourceLinks.self(id));
           return Response
                   .status(Response.Status.OK)
                   .entity(student)
                   .build();
       }catch (StudentResourceException e) {
           e.printStackTrace();
           return Response.status(Response.Status.NOT_FOUND).build();
       }
    }

    @GET
    public Response getStudentList() {
        try {
            List<Student> studentList = studentService.getStudentList();
            StudentResourceLinks resourceLinks = new StudentResourceLinks(uriInfo);
            if(studentList.size() < 1)
                throw new StudentResourceException("No StudentResource to display");
            for(Student s : studentList) {
                if(s != null)
                    s.addLink(resourceLinks.self(s.getId()));
            }

            GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(studentList){};
            return Response
                    .status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }catch (StudentResourceException e) {
            e.printStackTrace();
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(new GenericEntity<List<Student>>(new ArrayList<>()){})
                    .build();
        }
    }

    @POST
    public Response addStudent(Student student) {
        try {
            StudentResourceLinks resourceLinks = new StudentResourceLinks(uriInfo);
            student = studentService.addStudent(student);
            if(student == null)
                throw new StudentResourceException("No StudentResource to display");
            student.addLink(resourceLinks.self(student.getId()));
            return Response
                    .status(Response.Status.OK)
                    .entity(student)
                    .build();
        }catch (StudentResourceException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("{studentId}")
    public Response updateStudentById(@PathParam("studentId") long id, Student newStudent) {
        try {
            StudentResourceLinks resourceLinks = new StudentResourceLinks(uriInfo);
            Student student = studentService.updateStudentById(id, newStudent);
            if(student == null)
                throw new StudentResourceException("No StudentResource to display");
            student.addLink(resourceLinks.self(id));
            return Response
                    .status(Response.Status.OK)
                    .entity(student)
                    .build();
        }catch (StudentResourceException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("{studentId}")
    public Response deleteStudentById(@PathParam("studentId") long id) {
        try {
            StudentResourceLinks resourceLinks = new StudentResourceLinks(uriInfo);
            Student student = studentService.deleteStudentById(id);
            if(student == null)
                throw new StudentResourceException("No StudentResource to display");
            student.addLink(resourceLinks.self(id));
            return Response
                    .status(Response.Status.OK)
                    .entity(student)
                    .build();
        }catch (StudentResourceException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
