package com.itzroma.kpi.semester5.courseplatform.service;

import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityNotFoundException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Role;
import com.itzroma.kpi.semester5.courseplatform.model.User;

import java.util.List;

public interface UserService<ID, T extends User> {
    T register(T entity) throws EntityExistsException, ServiceException;

    boolean existsByEmail(String email) throws ServiceException;

    boolean existsByEmailAndPassword(String email, String password) throws ServiceException;

    T findByEmail(String email) throws EntityNotFoundException, ServiceException;

    Role getRoleByEmail(String email) throws ServiceException;

    T updateByUserId(ID targetId, T source) throws EntityExistsException, ServiceException;

    List<T> findMany(int quantity) throws ServiceException;

    List<T> findAll() throws ServiceException;
}
