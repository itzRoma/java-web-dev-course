package com.itzroma.kpi.semester5.courseplatform.service.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.StudentDao;
import com.itzroma.kpi.semester5.courseplatform.dao.impl.StudentDaoImpl;
import com.itzroma.kpi.semester5.courseplatform.db.Transaction;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Student;
import com.itzroma.kpi.semester5.courseplatform.service.StudentService;

import java.util.logging.Logger;

public class StudentServiceImpl extends UserServiceImpl<Student> implements StudentService {
    private static final Logger log = Logger.getLogger(StudentServiceImpl.class.getName());

    public StudentServiceImpl() {
        super(Student.class);
    }

    @Override
    public void toggleBlock(Student student) throws ServiceException {
        StudentDao dao = new StudentDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            dao.toggleBlock(student);
            log.info(
                    () -> "Toggle block for student with student id %d performed successfully"
                            .formatted(student.getStudentId())
            );
            transaction.commit();
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(ex.getMessage());
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public boolean isStudentBlocked(String email) throws ServiceException {
        StudentDao dao = new StudentDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            boolean blocked = dao.isStudentBlocked(email);
            transaction.commit();
            log.info("Student blocked status checked successfully");
            return blocked;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(ex.getMessage());
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }
}
