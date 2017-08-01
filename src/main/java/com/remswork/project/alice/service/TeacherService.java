package com.remswork.project.alice.service;

import java.util.List;

import com.remswork.project.alice.model.Teacher;

public interface TeacherService {
	
	Teacher addTeacher(Teacher teacher);
	List<Teacher> getTeacherList();
}
