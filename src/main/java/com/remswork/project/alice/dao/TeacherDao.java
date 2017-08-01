package com.remswork.project.alice.dao;

import java.util.List;

import com.remswork.project.alice.model.Teacher;

public interface TeacherDao {
	
	Teacher addTeacher(Teacher teacher);
	Teacher getTeacherById(int id);
	List<Teacher> getTeacherList();
	Teacher updateTeacherById(int id, Teacher newTeacher);
	Teacher deleteTeacherById(int id);
}
