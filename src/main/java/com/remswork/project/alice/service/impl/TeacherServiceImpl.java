package com.remswork.project.alice.service.impl;

import java.util.List;

import com.remswork.project.alice.exception.TeacherException;
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
	public Teacher getTeacherById(final long id) throws TeacherException {
		Teacher teacher = teacherDao.getTeacherById(id);
		if(teacher == null)
			throw new TeacherException("No teacher to return");
		return teacher;
	}
	
	@Override
	public List<Teacher> getTeacherList() throws TeacherException {
		List<Teacher> teacherList = teacherDao.getTeacherList();
		if(teacherList == null)
			throw new TeacherException("No teacher to return");
		return teacherList;
	}
	
	@Override
	public Teacher addTeacher(final Teacher teacher) throws TeacherException {
		Teacher t = teacherDao.addTeacher(teacher);
		if(t == null)
			throw new TeacherException("No teacher to return");
		return t;
	}

	@Override
	public Teacher addTeacher(Teacher teacher, long departmentId) throws TeacherException {
		Teacher t = teacherDao.addTeacher(teacher, departmentId);
		if(t == null)
			throw new TeacherException("No teacher to return");
		return t;
	}

	@Override
	public Teacher updateTeacherById(final long id, final Teacher newTeacher) throws TeacherException {
		Teacher teacher = teacherDao.updateTeacherById(id, newTeacher);
		if(teacher == null)
			throw new TeacherException("No teacher to return");
		return teacher;
	}

	@Override
	public Teacher updateTeacherById(long id, Teacher newTeacher, long departmentId) throws TeacherException {
		Teacher teacher = teacherDao.updateTeacherById(id, newTeacher, departmentId);
		if(teacher == null)
			throw new TeacherException("No teacher to return");
		return teacher;
	}

	@Override
	public Teacher deleteTeacherById(final long id) throws TeacherException {
		Teacher teacher = teacherDao.deleteTeacherById(id);
		if(teacher == null)
			throw new TeacherException("No teacher to return");
		return teacher;
	}

}
