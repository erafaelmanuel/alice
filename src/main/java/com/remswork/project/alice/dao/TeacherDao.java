package com.remswork.project.alice.dao;

import java.util.List;

import com.remswork.project.alice.model.Teacher;

public interface TeacherDao {
	
	Teacher getTeacherById(long id);
	List<Teacher> getTeacherList();
	Teacher addTeacher(Teacher teacher);
	Teacher addTeacher(Teacher teacher, long departmentId);
	Teacher updateTeacherById(long id, Teacher newTeacher);
	Teacher updateTeacherById(long id, Teacher newTeacher, long departmentId);
	Teacher deleteTeacherById(long id);
}
