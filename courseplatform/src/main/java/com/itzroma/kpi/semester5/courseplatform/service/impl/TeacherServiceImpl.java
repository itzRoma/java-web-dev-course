package com.itzroma.kpi.semester5.courseplatform.service.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.TeacherDao;
import com.itzroma.kpi.semester5.courseplatform.dao.impl.TeacherDaoImpl;
import com.itzroma.kpi.semester5.courseplatform.db.Transaction;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityNotFoundException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Teacher;
import com.itzroma.kpi.semester5.courseplatform.service.TeacherService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class TeacherServiceImpl extends UserServiceImpl<Teacher> implements TeacherService {
    private static final Logger log = Logger.getLogger(TeacherServiceImpl.class.getName());

    public TeacherServiceImpl() {
        super(Teacher.class);
    }

    @Override
    public Teacher register(Teacher entity) throws EntityExistsException, ServiceException {
        TeacherDao dao = new TeacherDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            if (dao.existsByEmail(entity.getEmail())) {
                transaction.closeTransaction();
                String message = "Email is already taken";
                log.warning(() -> "Cannot register new teacher: %s".formatted(message));
                throw new EntityExistsException(message);
            }
            Teacher teacher = dao.create(entity);
            transaction.commit();
            log.info(() -> "Registered new teacher with teacher id %s".formatted(teacher.getTeacherId()));
            return teacher;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.warning(() -> "Cannot register new teacher: %s".formatted(ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public Teacher findByEmail(String email) throws EntityNotFoundException, ServiceException {
        TeacherDao dao = new TeacherDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            Optional<Teacher> teacher = dao.findByEmail(email);
            transaction.commit();
            if (teacher.isEmpty()) {
                String message = "Teacher with provided email does not exist";
                log.warning(message);
                throw new EntityNotFoundException(message);
            }
            return teacher.get();
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.warning(() -> "Cannot find teacher by email: %s".formatted(ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public List<Teacher> findMany(int quantity) throws ServiceException {
        TeacherDao dao = new TeacherDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            List<Teacher> teachers = dao.findMany(quantity);
            transaction.commit();
            return teachers;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.warning(() -> "Cannot find %d teachers: %s".formatted(quantity, ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public List<Teacher> findAll() throws ServiceException {
        TeacherDao dao = new TeacherDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            List<Teacher> teachers = dao.findAll();
            transaction.commit();
            return teachers;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.warning(() -> "Cannot find all teachers: %s".formatted(ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }
}
