package com.itzroma.kpi.semester5.courseplatform.service;

import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Course;

import java.util.List;

public interface CourseService {
    Course create(Course course);

    List<Course> findMany(int quantity) throws ServiceException;

    List<Course> findAll() throws ServiceException;
}
