package com.itzroma.kpi.semester5.courseplatform.service.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.UnitDao;
import com.itzroma.kpi.semester5.courseplatform.dao.impl.UnitDaoImpl;
import com.itzroma.kpi.semester5.courseplatform.db.Transaction;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityNotFoundException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Unit;
import com.itzroma.kpi.semester5.courseplatform.service.UnitService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class UnitServiceImpl implements UnitService {
    private static final Logger log = Logger.getLogger(UnitServiceImpl.class.getName());

    @Override
    public Unit create(Unit unit) throws EntityExistsException, ServiceException {
        UnitDao dao = new UnitDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            if (dao.existsByTitleAndCourseId(unit.getTitle(), unit.getCourseId())) {
                transaction.closeTransaction();
                String message = "Unit '%s' is already exists for course with id %d"
                        .formatted(unit.getTitle(), unit.getCourseId());
                log.warning(() -> "Cannot create new unit: %s".formatted(message));
                throw new EntityExistsException(message);
            }
            Unit created = dao.create(unit);
            transaction.commit();
            log.info(() -> "New unit with id %d created successfully".formatted(created.getId()));
            return created;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot create new unit: %s".formatted(ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public List<Unit> findAllByCourseId(Long courseId) throws ServiceException {
        UnitDao dao = new UnitDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            List<Unit> units = dao.findAll();
            transaction.commit();
            log.info(
                    () -> "All (%d) units for course with id %d found successfully"
                            .formatted(units.size(), courseId)
            );
            return units;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(
                    () -> "Cannot find all units for course with id %d: %s"
                            .formatted(courseId, ex.getMessage())
            );
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public Unit findById(Long id) throws EntityNotFoundException, ServiceException {
        UnitDao dao = new UnitDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            Optional<Unit> unit = dao.findById(id);
            if (unit.isEmpty()) {
                transaction.commit();
                String message = "Unit with id %d not found".formatted(id);
                log.warning(message);
                throw new EntityNotFoundException(message);
            }
            transaction.commit();
            log.info(() -> "Unit with id %d found successfully".formatted(id));
            return unit.get();
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot find unit with id %d: %s".formatted(id, ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public Unit update(Unit target, Unit source) throws EntityExistsException, ServiceException {
        UnitDao dao = new UnitDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            if (
                    dao.existsByTitleAndCourseId(source.getTitle(), target.getCourseId())
                            && !target.getTitle().equals(source.getTitle())
            ) {
                transaction.closeTransaction();
                String message = "Unit '%s' is already exists for course with id %d"
                        .formatted(source.getTitle(), target.getCourseId());
                log.warning(() -> "Cannot update unit with id %d: %s".formatted(target.getId(), message));
                throw new EntityExistsException(message);
            }
            Unit updated = dao.update(target, source);
            transaction.commit();
            log.info(() -> "Unit with id %d updated successfully".formatted(target.getId()));
            return updated;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot update unit with id %d: %s".formatted(target.getId(), ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public void delete(Unit unit) throws ServiceException {
        UnitDao dao = new UnitDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        Long id = unit.getId();
        try {
            dao.delete(unit);
            transaction.commit();
            log.info(() -> "Unit with id %d deleted successfully".formatted(id));
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot delete unit with id %d: %s".formatted(id, ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }
}
