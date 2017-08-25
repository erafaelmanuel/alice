package com.remswork.project.alice.resource;

import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.resource.exception.TeacherResourceException;
import com.remswork.project.alice.resource.links.DepartmentResourceLinks;
import com.remswork.project.alice.resource.links.TeacherResourceLinks;
import com.remswork.project.alice.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Produces(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("teacher")
public class TeacherResource {
	
	@Autowired
	private TeacherServiceImpl teacherService;
	@Context
	private UriInfo uriInfo;
	@QueryParam("departmentId")
	private long departmentId;
	
	@GET
	@Path("{teacherId}")
	public Response getTeacherById(@PathParam("teacherId") long id) {
		try {
			TeacherResourceLinks resourceLink = new TeacherResourceLinks(uriInfo);
			DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
			Teacher teacher = teacherService.getTeacherById(id);

			if(teacher == null)
				throw new TeacherResourceException("No TeacherResource to display");

			teacher.addLink(resourceLink.self(id));
			if(teacher.getDepartment() != null)
				teacher.getDepartment().addLink(departmentResourceLinks.self(teacher.getDepartment().getId()));
			return Response
					.status(Response.Status.OK)
					.entity(teacher)
					.build();
		}catch (TeacherResourceException e){
			e.printStackTrace();
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	@GET
	public Response getTeacherList(){
		try {
			TeacherResourceLinks resourceLink = new TeacherResourceLinks(uriInfo);
			DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
			List<Teacher> teacherList = teacherService.getTeacherList();

			if(teacherList.size() < 1)
				throw new TeacherResourceException("No TeacherResource to display");

			for (Teacher t : teacherList) {
				t.addLink(resourceLink.self(t.getId()));
				if(t.getDepartment() != null)
					t.getDepartment().addLink(departmentResourceLinks.self(t.getDepartment().getId()));
			}

			GenericEntity<List<Teacher>> entity = new GenericEntity<List<Teacher>>(teacherList){};
			return Response
					.status(Response.Status.OK)
					.entity(entity)
					.build();
		}catch (TeacherResourceException e){
			e.printStackTrace();
			return Response
					.status(Response.Status.NO_CONTENT)
					.entity(new GenericEntity<List<Teacher>>(new ArrayList<>()){})
					.build();
		}
	}

	@POST
	public Response addTeacher(Teacher teacher) {
		try {
			TeacherResourceLinks resourceLink = new TeacherResourceLinks(uriInfo);
			DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
			if(departmentId > 0)
				teacher = teacherService.addTeacher(teacher, departmentId);
			else
				teacher = teacherService.addTeacher(teacher);
			if(teacher == null)
				throw new TeacherResourceException("No TeacherResource to display");
			teacher.addLink(resourceLink.self(teacher.getId()));
			if(teacher.getDepartment() != null)
				teacher.getDepartment().addLink(departmentResourceLinks.self(teacher.getDepartment().getId()));
			return Response
					.status(Response.Status.CREATED)
					.entity(teacher)
					.build();
		}catch (TeacherResourceException e){
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@PUT
	@Path("{teacherId}")
	public Response updateTeacherById(@PathParam("teacherId")long id, Teacher newTeacher) {
		try {
			TeacherResourceLinks resourceLink = new TeacherResourceLinks(uriInfo);
			DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
			Teacher teacher;
			if(departmentId > 0)
				teacher = teacherService.updateTeacherById(id, newTeacher, departmentId);
			else
				teacher = teacherService.updateTeacherById(id, newTeacher);
			if(teacher == null)
				throw new TeacherResourceException("No TeacherResource to display");

			teacher.addLink(resourceLink.self(id));
			if(teacher.getDepartment() != null)
				teacher.getDepartment().addLink(departmentResourceLinks.self(teacher.getDepartment().getId()));
			return Response
					.status(Response.Status.OK)
					.entity(teacher)
					.build();
		}catch (TeacherResourceException e){
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@DELETE
	@Path("{teacherId}")
	public Response deleteTeacherById(@PathParam("teacherId") long id) {
		try {
			TeacherResourceLinks resourceLink = new TeacherResourceLinks(uriInfo);
			DepartmentResourceLinks departmentResourceLinks = new DepartmentResourceLinks(uriInfo);
			Teacher teacher = teacherService.deleteTeacherById(id);

			if(teacher == null)
				throw new TeacherResourceException("No TeacherResource to display");

			teacher.addLink(resourceLink.self(id));
			if(teacher.getDepartment() != null)
				teacher.getDepartment().addLink(departmentResourceLinks.self(teacher.getDepartment().getId()));
			return Response
					.status(Response.Status.OK)
					.entity(teacher)
					.build();
		}catch (TeacherResourceException e){
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
}
