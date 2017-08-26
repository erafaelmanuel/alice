package com.remswork.project.alice.dao.impl;

import com.remswork.project.alice.bean.TimeHelperBean;
import com.remswork.project.alice.dao.ScheduleDao;
import com.remswork.project.alice.dao.exception.ScheduleDaoException;
import com.remswork.project.alice.exception.ScheduleException;
import com.remswork.project.alice.model.Schedule;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ScheduleDaoImpl implements ScheduleDao {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private TimeHelperBean timeHelperBean;

    @Override
    public Schedule getScheduleById(long id) throws ScheduleException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Schedule schedule = session.get(Schedule.class, id);
            if(schedule == null)
                throw new ScheduleDaoException("Schedule with id : " + id + " does not exist");
            session.getTransaction().commit();
            session.close();
            return schedule;
        }catch (ScheduleDaoException e) {
            session.close();
            throw new ScheduleException(e.getMessage());
        }
    }

    @Override
    public List<Schedule> getScheduleList() throws ScheduleException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Schedule> scheduleList = new ArrayList<>();
            Query query = session.createQuery("from Schedule");
            for(Object scheduleObj : query.list())
                scheduleList.add((Schedule) scheduleObj);
            session.getTransaction().commit();
            session.close();
            return scheduleList;
        }catch (ScheduleDaoException e) {
            session.close();
            throw new ScheduleException(e.getMessage());
        }
    }

    @Override
    public Schedule addSchedule(Schedule schedule) throws ScheduleException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            if (schedule == null)
                throw new ScheduleDaoException("You tried to add schedule with a null value");
            if (schedule.getDay() == null)
                throw new ScheduleDaoException("Schedule's day is required");
            if (schedule.getDay().trim().equals(""))
                throw new ScheduleDaoException("Schedule can't have an empty day");
            if (schedule.getTime() == null)
                throw new ScheduleDaoException("Schedule's time is required");
            if (schedule.getTime().trim().equals(""))
                throw new ScheduleDaoException("Schedule can't have an empty time");
            if(!timeHelperBean.isValid(schedule.getTime().trim()))
                throw new ScheduleDaoException("Schedule's time is invalid");
            if (schedule.getPeriod() == null)
                throw new ScheduleDaoException("Schedule's period is required");
            if (schedule.getPeriod().trim().equals(""))
                throw new ScheduleDaoException("Schedule can't have an empty period");
            if(!timeHelperBean.isValid(schedule.getPeriod().trim()))
                throw new ScheduleDaoException("Schedule's period is invalid");

            session.save(schedule);
            session.getTransaction().commit();
            session.close();
            return schedule;
        }catch (ScheduleDaoException e) {
            session.close();
            throw new ScheduleException(e.getMessage());
        }
    }

    @Override
    public Schedule updateScheduleById(long id, Schedule newSchedule) throws ScheduleException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Schedule schedule = session.get(Schedule.class, id);
            if(schedule == null)
                throw new ScheduleDaoException("Schedule with id : " + id + " does not exist");
            if(newSchedule == null)
                throw new ScheduleDaoException("You tried to update student with a null value");
            if(!(newSchedule.getDay() != null?newSchedule.getDay():"").trim().isEmpty())
                schedule.setDay(newSchedule.getDay());
            if(!(newSchedule.getTime() != null?newSchedule.getTime():"").trim().isEmpty()) {
                if(!timeHelperBean.isValid(newSchedule.getTime().trim()))
                    throw new ScheduleDaoException("Schedule's time is invalid");
                schedule.setTime(newSchedule.getTime());
            }
            if(!(newSchedule.getPeriod() != null?newSchedule.getPeriod():"").trim().isEmpty()) {
                if(!timeHelperBean.isValid(newSchedule.getPeriod().trim()))
                    throw new ScheduleDaoException("Schedule's period is invalid");
                schedule.setPeriod(newSchedule.getPeriod());
            }
            session.getTransaction().commit();
            session.close();
            return schedule;
        }catch (ScheduleDaoException e) {
            session.close();
            throw new ScheduleException(e.getMessage());
        }
    }

    @Override
    public Schedule deleteScheduleById(long id) throws ScheduleException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Schedule schedule = session.get(Schedule.class, id);
            if(schedule == null)
                throw new ScheduleDaoException("Schedule with id : " + id + " does not exist");
            session.delete(schedule);
            session.getTransaction().commit();
            session.close();
            return schedule;
        }catch (ScheduleDaoException e) {
            session.close();
            throw new ScheduleException(e.getMessage());
        }
    }
}