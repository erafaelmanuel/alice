package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.DepartmentDaoImpl;
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
    public Department getDepartmentById(final long id) {
        return departmentDao.getDepartmentById(id);
    }

    @Override
    public List<Department> getDepartmentList() {
        return departmentDao.getDepartmentList();
    }

    @Override
    public Department addDepartment(final Department department) {
        return departmentDao.addDepartment(department);
    }

    @Override
    public Department updateDepartmentById(final long id, final Department newDepartment) {
        return departmentDao.updateDepartmentById(id, newDepartment);
    }

    @Override
    public Department deleteDepartmentById(final long id) {
        return departmentDao.deleteDepartmentById(id);
    }
}

