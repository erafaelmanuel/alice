package com.remswork.project.alice.dao;

import com.remswork.project.alice.model.Department;

import java.util.List;

public interface DepartmentDao {

    Department getDepartmentById(long id);
    List<Department> getDepartmentList();
    Department addDepartment(Department department);
    Department updateDepartmentById(long id, Department newDepartment);
    Department deleteDepartmentById(long id);

}
