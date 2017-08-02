package com.remswork.project.alice.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.remswork.project.alice.dao.TeacherDao;
import com.remswork.project.alice.dao.exception.TeacherDaoException;
import com.remswork.project.alice.model.Department;
import com.remswork.project.alice.model.Teacher;

@Repository
public class TeacherDaoImpl implements TeacherDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Teacher getTeacherById(int id) {
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Teacher teacher = session.get(Teacher.class, id);
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
	
	@Override
	public Teacher addTeacher(Teacher teacher) {
		try {
			if(teacher == null)
				throw new TeacherDaoException(
						"You tried to add teacher with a null value");
			if(teacher.getFirstName().trim().equals(""))
				throw new TeacherDaoException(
						"Teacher can't have an empty first name");
			if(teacher.getLastName().trim().equals(""))
				throw new TeacherDaoException(
						"Teacher can't have an empty last name");
			if(teacher.getEmail().trim().equals(""))
				throw new TeacherDaoException(
						"Teacher can't have an empty email");
			if(teacher.getPassword().trim().equals(""))
				throw new TeacherDaoException(
						"Teacher can't have an empty password");
			
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			Department department = session.get(Department.class, 6);
			if(department != null)
				teacher.setDepartment(department);
			session.persist(teacher);
			session.getTransaction().commit();
			return teacher;
		}catch(TeacherDaoException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Teacher updateTeacherById(int id, Teacher newTeacher) {
		try {
			if(newTeacher == null)
				throw new TeacherDaoException(
						"You tried to add teacher with a null value");
			if(newTeacher.getFirstName().trim().equals(""))
				throw new TeacherDaoException(
						"Teacher can't have an empty first name");
			if(newTeacher.getLastName().trim().equals(""))
				throw new TeacherDaoException(
						"Teacher can't have an empty last name");
			if(newTeacher.getEmail().trim().equals(""))
				throw new TeacherDaoException(
						"Teacher can't have an empty email");
			if(newTeacher.getPassword().trim().equals(""))
				throw new TeacherDaoException(
						"Teacher can't have an empty password");
			
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			Teacher teacher = session.get(Teacher.class, id);
			if(teacher == null)
				throw new TeacherDaoException(String.format(Locale.ENGLISH , 
						"Teacher with id : %d does not exist.", id));
			teacher.setFirstName(newTeacher.getFirstName());
			teacher.setLastName(newTeacher.getLastName());
			teacher.setEmail(newTeacher.getEmail());
			teacher.setPassword(newTeacher.getPassword());
			teacher.setDepartment(newTeacher.getDepartment());
			session.persist(teacher);
			session.getTransaction().commit();
			return teacher;
			
		}catch(TeacherDaoException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Teacher deleteTeacherById(int id) {
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			Teacher teacher = session.get(Teacher.class, id);
			if(teacher != null)
				session.delete(teacher);
			else
				throw new TeacherDaoException(String.format(Locale.ENGLISH , 
						"Teacher with id : %d does not exist.", id));
			session.getTransaction().commit();
			return teacher;
			
		}catch(TeacherDaoException e) {
			e.printStackTrace();
			return null;
		}
	}

}
