package com.itzroma.kpi.semester5.courseplatform.dao;

import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Role;
import com.itzroma.kpi.semester5.courseplatform.model.User;

import java.util.Optional;

public interface UserDao<T extends User> extends CrudDao<Long, T> {
    boolean existsByEmail(String email) throws UnsuccessfulOperationException;

    boolean existsByEmailAndPassword(String email, String password) throws UnsuccessfulOperationException;

    Optional<T> findByEmail(String email) throws UnsuccessfulOperationException;

    Role getRoleByEmail(String email) throws UnsuccessfulOperationException;

    T updateByUserId(Long targetId, User source) throws UnsuccessfulOperationException;

    String getEmailByUserId(Long userId) throws UnsuccessfulOperationException;
}
