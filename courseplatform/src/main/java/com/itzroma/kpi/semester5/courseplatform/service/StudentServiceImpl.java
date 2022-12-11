package com.itzroma.kpi.semester5.courseplatform.service;

import com.itzroma.kpi.semester5.courseplatform.dao.StudentDao;
import com.itzroma.kpi.semester5.courseplatform.dao.impl.StudentDaoImpl;
import com.itzroma.kpi.semester5.courseplatform.db.Transaction;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Student;

import java.util.logging.Logger;

public class StudentServiceImpl {
    private static final Logger log = Logger.getLogger(StudentServiceImpl.class.getName());

    public Student create(Student entity) throws ServiceException {
        StudentDao dao = new StudentDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            if (dao.existsByEmail(entity.getEmail())) {
                String msg = "Email %s is already taken".formatted(entity.getEmail());
                log.severe(() -> "Cannot create new student: %s".formatted(msg));
                throw new EntityExistsException(msg);
            }
            Student student = dao.create(entity);
            transaction.commit();
            log.info(() -> "New student created: %s".formatted(student));
            return student;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot create new student: %s".formatted(ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }
}
