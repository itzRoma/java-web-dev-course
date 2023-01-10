package com.itzroma.kpi.semester5.courseplatform.service.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.CourseDao;
import com.itzroma.kpi.semester5.courseplatform.dao.impl.CourseDaoImpl;
import com.itzroma.kpi.semester5.courseplatform.db.Transaction;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityNotFoundException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Course;
import com.itzroma.kpi.semester5.courseplatform.service.CourseService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class CourseServiceImpl implements CourseService {
    private static final Logger log = Logger.getLogger(CourseServiceImpl.class.getName());

    @Override
    public Course create(Course course) {
        CourseDao dao = new CourseDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            Course created = dao.create(course);
            transaction.commit();
            log.info(() -> "New course with id %d created successfully".formatted(created.getId()));
            return created;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot create new course: %s".formatted(ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public List<Course> findMany(int quantity) throws ServiceException {
        CourseDao dao = new CourseDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            List<Course> courses = dao.findMany(quantity);
            transaction.commit();
            log.info(
                    () -> "%d out of %d requested courses found successfully"
                            .formatted(courses.size(), quantity)
            );
            return courses;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot find %d courses: %s".formatted(quantity, ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public List<Course> findAll() throws ServiceException {
        CourseDao dao = new CourseDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            List<Course> courses = dao.findAll();
            transaction.commit();
            log.info(() -> "All (%d) courses found successfully".formatted(courses.size()));
            return courses;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot find all courses: %s".formatted(ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public Course findById(Long id) throws EntityNotFoundException, ServiceException {
        CourseDao dao = new CourseDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            Optional<Course> course = dao.findById(id);
            transaction.commit();
            if (course.isEmpty()) {
                String message = "Course with id %d not found".formatted(id);
                log.warning(message);
                throw new EntityNotFoundException(message);
            }
            log.info(() -> "Course with id %d found successfully".formatted(id));
            return course.get();
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot find course with id %d: %s".formatted(id, ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public void delete(Course course) throws ServiceException {
        CourseDao dao = new CourseDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        Long id = course.getId();
        try {
            dao.delete(course);
            transaction.commit();
            log.info(() -> "Course with id %d deleted successfully".formatted(id));
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot delete course with id %d: %s".formatted(id, ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }
}
