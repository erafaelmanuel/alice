package com.remswork.project.alice.dao;

import java.util.List;

import com.remswork.project.alice.model.Teacher;

public interface TeacherDao {
	
	Teacher addTeacher(Teacher teacher);
	List<Teacher> getTeacherList();
}
