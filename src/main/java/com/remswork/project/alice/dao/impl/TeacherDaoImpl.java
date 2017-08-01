package com.remswork.project.alice.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.remswork.project.alice.dao.TeacherDao;
import com.remswork.project.alice.exception.TeacherDaoException;
import com.remswork.project.alice.model.Teacher;

@Repository
public class TeacherDaoImpl implements TeacherDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Teacher addTeacher(Teacher teacher) {
		try {
			if(teacher == null)
				throw new TeacherDaoException("You tried to add teacher with a null value");
			
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(teacher);
			session.getTransaction().commit();
			return teacher;
		}catch(TeacherDaoException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Teacher> getTeacherList() {
		final List<Teacher> teacherList = new ArrayList<>();
		Query query = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			query = session.createQuery("from Teacher");
			session.getTransaction().commit();
			
			for(Object teacherObj :  query.list())
				teacherList.add((Teacher) teacherObj);
			
			return teacherList;
		}catch(TeacherDaoException e) {
			e.printStackTrace();
			return teacherList;
		}
	}

}
