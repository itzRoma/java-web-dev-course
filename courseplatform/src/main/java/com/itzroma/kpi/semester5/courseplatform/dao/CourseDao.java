package com.itzroma.kpi.semester5.courseplatform.dao;

import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Course;

public interface CourseDao extends CrudDao<Long, Course> {
    Course updateByCourseId(Long targetId, Course source) throws UnsuccessfulOperationException;
}
