package com.remswork.project.alice.service;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Activity;

import java.util.List;

public interface ActivityService {

    Activity getActivityById(long id) throws GradingFactorException;

    List<Activity> getActivityList() throws GradingFactorException;

    List<Activity> getActivityListByStudentAndSubjectId(long studentId, long subjectId) throws GradingFactorException;

    List<Activity> getActivityListByStudentAndSubjectId(long studentId, long subjectId, long termId)
            throws GradingFactorException;

    Activity addActivity(Activity activity, long studentId, long subjectId) throws GradingFactorException;

    Activity addActivity(Activity activity, long studentId, long subjectId, long termId) throws GradingFactorException;

    Activity updateActivityById(long id, Activity newActivity, long studentId, long subjectId)
            throws GradingFactorException;

    Activity updateActivityById(long id, Activity newActivity, long studentId, long subjectId, long termId)
            throws GradingFactorException;

    Activity deleteActivityById(long id) throws GradingFactorException;
}
