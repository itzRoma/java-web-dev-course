package com.itzroma.kpi.semester5.courseplatform.dao;

import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.User;

public interface UserDao<T extends User> extends CrudDao<Long, T> {
    boolean existsByEmail(String email) throws UnsuccessfulOperationException;
}
