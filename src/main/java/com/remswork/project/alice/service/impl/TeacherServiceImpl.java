package com.remswork.project.alice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remswork.project.alice.dao.impl.TeacherDaoImpl;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherDaoImpl teacherDao;

	@Override
	public Teacher getTeacherById(final long id) {
		return teacherDao.getTeacherById(id);
	}
	
	@Override
	public List<Teacher> getTeacherList() {
		return teacherDao.getTeacherList();
	}
	
	@Override
	public Teacher addTeacher(final Teacher teacher) {
		return teacherDao.addTeacher(teacher);
	}

	@Override
	public Teacher addTeacher(Teacher teacher, long departmentId) {
		return teacherDao.addTeacher(teacher, departmentId);
	}

	@Override
	public Teacher updateTeacherById(final long id, final Teacher newTeacher) {
		return teacherDao.updateTeacherById(id, newTeacher);
	}

	@Override
	public Teacher updateTeacherById(long id, Teacher newTeacher, long departmentId) {
		return teacherDao.updateTeacherById(id, newTeacher, departmentId);
	}

	@Override
	public Teacher deleteTeacherById(final long id) {
		return teacherDao.deleteTeacherById(id);
	}

}
