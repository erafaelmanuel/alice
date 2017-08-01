package com.remswork.project.alice.res;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
	public List<Teacher> getTeacherList(){
		return teacherService.getTeacherList();
	}

}
