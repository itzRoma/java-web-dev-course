package com.itzroma.kpi.semester5.courseplatform.service.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.StudentDao;
import com.itzroma.kpi.semester5.courseplatform.dao.impl.StudentDaoImpl;
import com.itzroma.kpi.semester5.courseplatform.db.Transaction;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityNotFound;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Student;
import com.itzroma.kpi.semester5.courseplatform.service.StudentService;

import java.util.Optional;
import java.util.logging.Logger;

public class StudentServiceImpl extends UserServiceImpl<Student> implements StudentService {
    private static final Logger log = Logger.getLogger(StudentServiceImpl.class.getName());

    public StudentServiceImpl() {
        super(Student.class);
    }

    @Override
    public Student register(Student entity) throws EntityExistsException, ServiceException {
        StudentDao dao = new StudentDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            if (dao.existsByEmail(entity.getEmail())) {
                transaction.closeTransaction();
                String message = "Email is already taken";
                log.warning(() -> "Cannot register new student: %s".formatted(message));
                throw new EntityExistsException(message);
            }
            Student student = dao.create(entity);
            transaction.commit();
            return student;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.warning(() -> "Cannot register new student: %s".formatted(ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public Student findByEmail(String email) throws ServiceException {
        StudentDao dao = new StudentDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            Optional<Student> student = dao.findByEmail(email);
            transaction.commit();
            if (student.isEmpty()) {
                String message = "Student with provided email does not exist";
                log.warning(message);
                throw new EntityNotFound(message);
            }
            return student.get();
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.warning(() -> "Cannot find student by email: %s".formatted(ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }
}