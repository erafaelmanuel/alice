package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.DepartmentDaoImpl;
import com.remswork.project.alice.exception.DepartmentException;
import com.remswork.project.alice.model.Department;
import com.remswork.project.alice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDaoImpl departmentDao;

    @Override
    public Department getDepartmentById(final long id) throws DepartmentException {
        Department department = departmentDao.getDepartmentById(id);
        if(department == null)
            throw new DepartmentException("No Department to return");
        return department;
    }

    @Override
    public List<Department> getDepartmentList() throws DepartmentException {
        List<Department> departmentList = departmentDao.getDepartmentList();
        if(departmentList == null)
            throw new DepartmentException("No Department to return");
        return departmentList;
    }

    @Override
    public Department addDepartment(final Department department) throws DepartmentException {
        Department d = departmentDao.addDepartment(department);
        if(d == null)
            throw new DepartmentException("No Department to return");
        return d;
    }

    @Override
    public Department updateDepartmentById(final long id, final Department newDepartment) throws DepartmentException {
        Department department = departmentDao.updateDepartmentById(id, newDepartment);
        if(department == null)
            throw new DepartmentException("No Department to return");
        return department;
    }

    @Override
    public Department deleteDepartmentById(final long id) throws DepartmentException {
        Department department = departmentDao.deleteDepartmentById(id);
        if(department == null)
            throw new DepartmentException("No Department to return");
        return department;
    }
}

