package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.ClassDaoImpl;
import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.model.Schedule;
import com.remswork.project.alice.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassDaoImpl classDao;

    @Override
    public Class getClassById(long id) throws ClassException {
        return classDao.getClassById(id);
    }

    @Override
    public List<Class> getClassList() throws ClassException {
        return classDao.getClassList();
    }

    @Override
    public Schedule getScheduleById(long classId, long id) throws ClassException {
        return classDao.getScheduleById(classId, id);
    }

    @Override
    public Set<Schedule> getScheduleList(long classId) throws ClassException {
        return classDao.getScheduleList(classId);
    }

    @Override
    public Class addClass(Class _class, long teacherId, long subjectId, long sectionId) throws ClassException {
        return classDao.addClass(_class, teacherId, subjectId, sectionId);
    }

    @Override
    public Schedule addScheduleById(long classId, long id) throws ClassException {
        return classDao.addScheduleById(classId, id);
    }

    @Override
    public Class updateClassId(long id, Class newClass, long teacherId, long subjectId, long sectionId)
            throws ClassException {
        return classDao.updateClassId(id, newClass, teacherId, subjectId, sectionId);
    }

    @Override
    public Class deleteClassById(long id) throws ClassException {
        return classDao.deleteClassById(id);
    }

    @Override
    public Schedule deleteScheduleById(long classId, long id) throws ClassException {
        return classDao.deleteScheduleById(classId, id);
    }
}
