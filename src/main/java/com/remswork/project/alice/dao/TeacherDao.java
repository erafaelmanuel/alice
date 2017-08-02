package com.remswork.project.alice.dao;

import java.util.List;

import com.remswork.project.alice.model.Teacher;

public interface TeacherDao {
	
	Teacher getTeacherById(int id);
	List<Teacher> getTeacherList();
	Teacher addTeacher(Teacher teacher);
	Teacher updateTeacherById(int id, Teacher newTeacher);
	Teacher deleteTeacherById(int id);
}
