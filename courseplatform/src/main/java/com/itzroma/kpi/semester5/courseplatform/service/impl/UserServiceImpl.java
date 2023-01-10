package com.itzroma.kpi.semester5.courseplatform.service.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.UserDao;
import com.itzroma.kpi.semester5.courseplatform.dao.impl.AdminDaoImpl;
import com.itzroma.kpi.semester5.courseplatform.dao.impl.StudentDaoImpl;
import com.itzroma.kpi.semester5.courseplatform.dao.impl.TeacherDaoImpl;
import com.itzroma.kpi.semester5.courseplatform.db.Transaction;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityNotFoundException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.*;
import com.itzroma.kpi.semester5.courseplatform.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public abstract class UserServiceImpl<T extends User> implements UserService<Long, T> {
    private UserDao<T> dao;
    private final String entityClassName;

    private static final Logger log = Logger.getLogger(UserServiceImpl.class.getName());

    protected UserServiceImpl(Class<T> entity) {
        entityClassName = entity.getSimpleName();

        if (entity.isAssignableFrom(Student.class)) {
            dao = (UserDao<T>) new StudentDaoImpl();
        } else if (entity.isAssignableFrom(Teacher.class)) {
            dao = (UserDao<T>) new TeacherDaoImpl();
        } else if (entity.isAssignableFrom(Admin.class)) {
            dao = (UserDao<T>) new AdminDaoImpl();
        }
    }

    @Override
    public T register(T entity) throws EntityExistsException, ServiceException {
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            if (dao.existsByEmail(entity.getEmail())) {
                transaction.closeTransaction();
                String message = "Email is already taken";
                log.warning(() -> "Cannot register new %s: %s".formatted(entityClassName, message));
                throw new EntityExistsException(message);
            }
            T user = dao.create(entity);
            transaction.commit();
            log.info(
                    () -> "New %s with user id %d registered successfully"
                            .formatted(entityClassName, user.getUserId())
            );
            return user;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot register new %s: %s".formatted(entityClassName, ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public boolean existsByEmail(String email) throws ServiceException {
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            boolean existsByEmail = dao.existsByEmail(email);
            transaction.commit();
            log.info(() -> "%s existence by email checked successfully".formatted(entityClassName));
            return existsByEmail;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(
                    () -> "Cannot check %s existence by email: %s"
                            .formatted(entityClassName, ex.getMessage())
            );
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
            log.info(
                    () -> "%s existence by email and password checked successfully"
                            .formatted(entityClassName)
            );
            return existsByEmailAndPassword;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(
                    () -> "Cannot check %s existence by email and password: %s"
                            .formatted(entityClassName, ex.getMessage())
            );
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public T findByEmail(String email) throws EntityNotFoundException, ServiceException {
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            Optional<T> user = dao.findByEmail(email);
            transaction.commit();
            if (user.isEmpty()) {
                String message = "%s with provided email does not exist".formatted(entityClassName);
                log.warning(message);
                throw new EntityNotFoundException(message);
            }
            log.info("User with provided email found successfully");
            return user.get();
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot find %s by email: %s".formatted(entityClassName, ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public Role getUserRoleByEmail(String email) throws ServiceException {
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            Role role = dao.getUserRoleByEmail(email);
            transaction.commit();
            log.info("User role by email found successfully");
            return role;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot get user role by email: %s".formatted(ex.getMessage()));
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
            if (
                    dao.existsByEmail(source.getEmail())
                            && !dao.getEmailByUserId(targetId).equals(source.getEmail())
            ) {
                transaction.closeTransaction();
                String message = "Email is already taken";
                log.warning(
                        () -> "Cannot update %s with user id %d: %s"
                                .formatted(entityClassName, targetId, message)
                );
                throw new EntityExistsException(message);
            }
            T updated = dao.updateByUserId(targetId, source);
            transaction.commit();
            log.info(
                    () -> "%s with user id %d updated successfully"
                            .formatted(entityClassName, targetId)
            );
            return updated;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(
                    () -> "Cannot update %s with user id %d: %s"
                            .formatted(entityClassName, targetId, ex.getMessage())
            );
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public List<T> findMany(int quantity) throws ServiceException {
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            List<T> users = dao.findMany(quantity);
            transaction.commit();
            log.info(
                    () -> "%d out of %d requested %ss found successfully"
                            .formatted(users.size(), quantity, entityClassName)
            );
            return users;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(
                    () -> "Cannot find %d %ss: %s"
                            .formatted(quantity, entityClassName, ex.getMessage())
            );
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public List<T> findAll() throws ServiceException {
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            List<T> users = dao.findAll();
            transaction.commit();
            log.info(() -> "All (%d) %ss found successfully".formatted(users.size(), entityClassName));
            return users;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.warning(() -> "Cannot find all %ss: %s".formatted(entityClassName, ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }
}
