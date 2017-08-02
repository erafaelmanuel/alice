package com.remswork.project.alice.res;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.remswork.project.alice.res.link.TeacherResourceLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.service.impl.TeacherServiceImpl;

@Component
@Produces(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value={MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("teacher")
public class TeacherResource {
	
	@Autowired
	private TeacherServiceImpl teacherService;
	@Context
	private UriInfo uriInfo;
	
	@GET
	@Path("{teacherId}")
	public Teacher getTeacherById(@PathParam("teacherId") int id) {
		TeacherResourceLink resourceLink = new TeacherResourceLink(uriInfo);
		Teacher teacher = teacherService.getTeacherById(id);
		teacher.addLink(resourceLink.self(id));
		return teacher;
	}
	
	@GET
	public List<Teacher> getTeacherList(){
		TeacherResourceLink resourceLink = new TeacherResourceLink(uriInfo);
		List<Teacher> teacherList = teacherService.getTeacherList();
		for(Teacher t : teacherList) {
			t.addLink(resourceLink.self(t.getId()));
		}
		return teacherList;
	}

	@POST
	public Teacher addTeacher(Teacher teacher) {
		TeacherResourceLink resourceLink = new TeacherResourceLink(uriInfo);
		teacher.addLink(resourceLink.self(teacher.getId()));
		return teacherService.addTeacher(teacher);
	}

	@PUT
	@Path("{teacherId}")
	public Teacher updateTeacherById(@PathParam("teacherId")int id, Teacher newTeacher) {
		TeacherResourceLink resourceLink = new TeacherResourceLink(uriInfo);
		Teacher teacher = teacherService.updateTeacherById(id, newTeacher);
		teacher.addLink(resourceLink.self(id));
		return teacher;
	}
	
	@DELETE
	@Path("{teacherId}")
	public Teacher deleteTeacherById(@PathParam("teacherId") int id) {
		TeacherResourceLink resourceLink = new TeacherResourceLink(uriInfo);
		Teacher teacher = teacherService.deleteTeacherById(id);
		teacher.addLink(resourceLink.self(id));
		return teacher;
	}
}
