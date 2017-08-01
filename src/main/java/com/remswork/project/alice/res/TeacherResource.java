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
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.service.impl.TeacherServiceImpl;

@Component
@Path("teacher")
public class TeacherResource {
	
	@Autowired
	private TeacherServiceImpl teacherService;
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Teacher addTeacher(Teacher teacher) {
		return teacherService.addTeacher(teacher);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{teacherId}")
	public Teacher getTeacherById(@PathParam("teacherId") int id) {
		return teacherService.getTeacherById(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Teacher> getTeacherList(){
		return teacherService.getTeacherList();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{teacherId}")
	public Teacher updateTeacherById(@PathParam("teacherId")int id, Teacher newTeacher) {
		return teacherService.updateTeacherById(id, newTeacher);
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{teacherId}")
	public Teacher deleteTeacherById(@PathParam("teacherId") int id) {
		return teacherService.deleteTeacherById(id);
	}
}
