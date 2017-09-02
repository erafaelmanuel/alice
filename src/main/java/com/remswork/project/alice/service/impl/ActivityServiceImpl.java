package com.remswork.project.alice.service.impl;

import com.remswork.project.alice.dao.impl.ActivityDaoImpl;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Activity;
import com.remswork.project.alice.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDaoImpl activityDao;


    @Override
    public Activity getActivityById(long id) throws GradingFactorException {
        return activityDao.getActivityById(id);
    }

    @Override
    public List<Activity> getActivityList() throws GradingFactorException {
        return activityDao.getActivityList();
    }

    @Override
    public List<Activity> getActivityListByStudentAndSubjectId(long studentId, long subjectId)
            throws GradingFactorException {
        return activityDao.getActivityListByStudentAndSubjectId(studentId, subjectId);
    }

    @Override
    public List<Activity> getActivityListByStudentAndSubjectId(long studentId, long subjectId, long termId)
            throws GradingFactorException {
        return activityDao.getActivityListByStudentAndSubjectId(studentId, subjectId, termId);
    }

    @Override
    public Activity addActivity(Activity activity, long studentId, long subjectId) throws GradingFactorException {
        return activityDao.addActivity(activity, studentId, subjectId);
    }

    @Override
    public Activity addActivity(Activity activity, long studentId, long subjectId, long termId)
            throws GradingFactorException {
        return activityDao.addActivity(activity, studentId, subjectId, termId);
    }

    @Override
    public Activity updateActivityById(long id, Activity newActivity, long studentId, long subjectId)
            throws GradingFactorException {
        return activityDao.updateActivityById(id, newActivity, studentId, subjectId);
    }

    @Override
    public Activity updateActivityById(long id, Activity newActivity, long studentId, long subjectId,
                                       long termId) throws GradingFactorException {
        return activityDao.updateActivityById(id, newActivity, studentId, subjectId, termId);
    }

    @Override
    public Activity deleteActivityById(long id) throws GradingFactorException {
        return activityDao.deleteActivityById(id);
    }
}
