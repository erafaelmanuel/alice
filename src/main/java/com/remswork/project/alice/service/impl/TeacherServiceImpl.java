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
	public Teacher addTeacher(Teacher teacher) {
		return teacherDao.addTeacher(teacher);
	}

	@Override
	public List<Teacher> getTeacherList() {
		return teacherDao.getTeacherList();
	}

}
