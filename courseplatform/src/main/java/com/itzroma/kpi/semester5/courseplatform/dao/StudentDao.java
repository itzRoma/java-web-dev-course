package com.itzroma.kpi.semester5.courseplatform.dao;

import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Student;

public interface StudentDao extends UserDao<Student> {
    void toggleBlock(Student student) throws UnsuccessfulOperationException;

    boolean isStudentBlocked(String email) throws UnsuccessfulOperationException;
}
