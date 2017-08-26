package com.remswork.project.alice.dao;

import com.remswork.project.alice.exception.ScheduleException;
import com.remswork.project.alice.model.Schedule;

import java.util.List;

public interface ScheduleDao {

    Schedule getScheduleById(long id) throws ScheduleException;
    List<Schedule> getScheduleList() throws ScheduleException;
    Schedule addSchedule(Schedule schedule) throws ScheduleException;
    Schedule updateScheduleById(long id, Schedule newSchedule) throws ScheduleException;
    Schedule deleteScheduleById(long id) throws ScheduleException;

}
