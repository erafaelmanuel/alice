package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.AttendanceDao;
import com.remswork.project.alice.dao.impl.AttendanceDaoImpl;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceDao {

    @Autowired
    private AttendanceDaoImpl attendanceDao;


    @Override
    public Attendance getAttendanceById(long id) throws GradingFactorException {
        return attendanceDao.getAttendanceById(id);
    }

    @Override
    public List<Attendance> getAttendanceList() throws GradingFactorException {
        return attendanceDao.getAttendanceList();
    }

    @Override
    public List<Attendance> getAttendanceListByStudentAndSubjectId(long studentId, long subjectId)
            throws GradingFactorException {
        return attendanceDao.getAttendanceListByStudentAndSubjectId(studentId, subjectId);
    }

    @Override
    public Attendance addAttendance(Attendance attendance, long studentId, long subjectId) throws GradingFactorException {
        return attendanceDao.addAttendance(attendance, studentId, subjectId);
    }

    @Override
    public Attendance updateAttendanceById(long id, Attendance newAttendance, long studentId, long subjectId)
            throws GradingFactorException {
        return attendanceDao.updateAttendanceById(id, newAttendance, studentId, subjectId);
    }

    @Override
    public Attendance deleteAttendanceById(long id) throws GradingFactorException {
        return attendanceDao.deleteAttendanceById(id);
    }
}