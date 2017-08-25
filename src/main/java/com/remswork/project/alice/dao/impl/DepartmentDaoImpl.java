package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.dao.DepartmentDao;
import com.remswork.project.alice.dao.exception.DepartmentDaoException;
import com.remswork.project.alice.model.Department;
import com.remswork.project.alice.model.Teacher;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Department getDepartmentById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            Department department = session.get(Department.class, id);
            if(department == null)
                throw new DepartmentDaoException("Department with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return department;
        }catch (DepartmentDaoException e){
            e.printStackTrace();
            session.close();
            return null;
        }
    }

    @Override
    public List<Department> getDepartmentList() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Department> departmentList = new ArrayList<>();
        try{
            Query query = session.createQuery("from Department");
            for(Object departmentObj : query.list())
                departmentList.add((Department) departmentObj);
            session.getTransaction().commit();
            session.close();
            return departmentList;
        }catch (DepartmentDaoException e){
            e.printStackTrace();
            session.close();
            return departmentList;
        }
    }

    @Override
    public Department addDepartment(Department department) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            if(department == null)
                throw new DepartmentDaoException(
                        "You tried to add department with a null value");
            if(department.getName().trim().equals(""))
                throw new DepartmentDaoException(
                        "Department can't have an empty department name");
            if(department.getDescription().trim().equals(""))
                throw new DepartmentDaoException(
                        "Department can't have an empty department description");

            session.save(department);
            session.getTransaction().commit();
            session.close();
            return department;
        }catch(DepartmentDaoException e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }

    @Override
    public Department updateDepartmentById(long id, Department newDepartment) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            Department department = session.get(Department.class, id);

            if(newDepartment == null)
                throw new DepartmentDaoException("You tried to add department with a null value");
            if(department == null)
                throw new DepartmentDaoException("Department with id : " + id + " does not exist.");

            if(!(newDepartment.getName()!=null?newDepartment.getName().trim():"").isEmpty())
                department.setName(newDepartment.getName());
            if(!(newDepartment.getDescription()!=null?newDepartment.getDescription().trim():"").isEmpty())
                department.setDescription(newDepartment.getDescription());

            session.getTransaction().commit();
            session.close();
            return department;
        }catch(DepartmentDaoException e){
            e.printStackTrace();
            session.close();
            return null;
        }
    }

    @Override
    public Department deleteDepartmentById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Department department = session.get(Department.class, id);
            if(department != null) {
                //to avoid the constraints restriction we meed to delete the teacher with had the department
                for(Object teacherObj : session.createQuery("from Teacher").list()){
                    Teacher teacher = (Teacher) teacherObj;
                    if(teacher.getDepartment().equals(department) ||
                            (teacher.getDepartment()!=null?teacher.getDepartment().getId():0)==department.getId()) {
                        session.delete(teacher);
                    }
                }
                session.delete(department);
            }else
                throw new DepartmentDaoException("Department with id : " + id + " does not exist.");
            session.getTransaction().commit();
            session.close();
            return department;
        }catch(DepartmentDaoException e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }
}
