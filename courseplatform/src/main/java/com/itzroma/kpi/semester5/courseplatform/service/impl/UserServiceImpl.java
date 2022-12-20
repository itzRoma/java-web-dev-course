package com.itzroma.kpi.semester5.courseplatform.service.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.UserDao;
import com.itzroma.kpi.semester5.courseplatform.dao.impl.AdminDaoImpl;
import com.itzroma.kpi.semester5.courseplatform.dao.impl.StudentDaoImpl;
import com.itzroma.kpi.semester5.courseplatform.db.Transaction;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Admin;
import com.itzroma.kpi.semester5.courseplatform.model.Role;
import com.itzroma.kpi.semester5.courseplatform.model.Student;
import com.itzroma.kpi.semester5.courseplatform.model.User;
import com.itzroma.kpi.semester5.courseplatform.service.UserService;

import java.util.logging.Logger;

public abstract class UserServiceImpl<T extends User> implements UserService<Long, T> {
    private UserDao<T> dao;

    private static final Logger log = Logger.getLogger(UserServiceImpl.class.getName());

    protected UserServiceImpl(Class<T> entity) {
        if (entity.isAssignableFrom(Student.class)) {
            dao = (UserDao<T>) new StudentDaoImpl();
        } else if (entity.isAssignableFrom(Admin.class)) {
            dao = (UserDao<T>) new AdminDaoImpl();
        }
    }

    @Override
    public boolean existsByEmail(String email) throws ServiceException {
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            boolean existsByEmail = dao.existsByEmail(email);
            transaction.commit();
            return existsByEmail;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.warning(() -> "Cannot check user existence by email: %s".formatted(ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public boolean existsByEmailAndPassword(String email, String password) throws ServiceException {
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            boolean existsByEmailAndPassword = dao.existsByEmailAndPassword(email, password);
            transaction.commit();
            return existsByEmailAndPassword;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.warning(() -> "Cannot check student existence by email and password: %s".formatted(ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public Role getRoleByEmail(String email) throws ServiceException {
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            Role role = dao.getRoleByEmail(email);
            transaction.commit();
            return role;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.warning(() -> "Cannot get user role by email: %s".formatted(ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public T updateByUserId(Long targetId, T source) throws EntityExistsException, ServiceException {
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            if (dao.existsByEmail(source.getEmail()) && !dao.getEmailByUserId(targetId).equals(source.getEmail())) {
                transaction.closeTransaction();
                String message = "Email is already taken";
                log.warning(() -> "Cannot update user with id %d: %s".formatted(targetId, message));
                throw new EntityExistsException(message);
            }
            T updated = dao.updateByUserId(targetId, source);
            transaction.commit();
            log.info(() -> "Updated user with id %d".formatted(targetId));
            return updated;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.warning(() -> "Cannot update user with id %d: %s".formatted(targetId, ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }
}
