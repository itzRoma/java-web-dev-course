package com.itzroma.kpi.semester5.courseplatform.service.impl;

import com.itzroma.kpi.semester5.courseplatform.model.Teacher;
import com.itzroma.kpi.semester5.courseplatform.service.TeacherService;

import java.util.logging.Logger;

public class TeacherServiceImpl extends UserServiceImpl<Teacher> implements TeacherService {
    private static final Logger log = Logger.getLogger(TeacherServiceImpl.class.getName());

    public TeacherServiceImpl() {
        super(Teacher.class);
    }
}
