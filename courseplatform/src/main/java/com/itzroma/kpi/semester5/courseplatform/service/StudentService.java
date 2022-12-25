package com.itzroma.kpi.semester5.courseplatform.service;

import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Student;

public interface StudentService extends UserService<Long, Student> {
    void toggleBlock(Student student) throws ServiceException;
    boolean isStudentBlocked(String email) throws ServiceException;
}
