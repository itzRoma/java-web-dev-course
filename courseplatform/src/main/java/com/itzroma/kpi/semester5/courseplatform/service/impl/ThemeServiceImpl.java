package com.itzroma.kpi.semester5.courseplatform.service.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.ThemeDao;
import com.itzroma.kpi.semester5.courseplatform.dao.impl.ThemeDaoImpl;
import com.itzroma.kpi.semester5.courseplatform.db.Transaction;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityNotFoundException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Theme;
import com.itzroma.kpi.semester5.courseplatform.service.ThemeService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ThemeServiceImpl implements ThemeService {
    private static final Logger log = Logger.getLogger(ThemeServiceImpl.class.getName());

    @Override
    public Theme create(Theme theme) {
        ThemeDao dao = new ThemeDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            if (dao.existsByName(theme.getName())) {
                transaction.closeTransaction();
                String message = "Theme '%s' is already exists".formatted(theme.getName());
                log.warning(() -> "Cannot create new theme: %s".formatted(message));
                throw new EntityExistsException(message);
            }
            Theme created = dao.create(theme);
            transaction.commit();
            log.info(() -> "New theme '%s' created successfully".formatted(created.getName()));
            return created;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot create new theme: %s".formatted(ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public List<Theme> findMany(int quantity) throws ServiceException {
        ThemeDao dao = new ThemeDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            List<Theme> themes = dao.findMany(quantity);
            transaction.commit();
            log.info(
                    () -> "%d out of %d requested themes found successfully"
                            .formatted(themes.size(), quantity)
            );
            return themes;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot find %d themes: %s".formatted(quantity, ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public List<Theme> findAll() throws ServiceException {
        ThemeDao dao = new ThemeDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            List<Theme> themes = dao.findAll();
            transaction.commit();
            log.info(() -> "All (%d) themes found successfully".formatted(themes.size()));
            return themes;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot find all themes: %s".formatted(ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public Theme findById(Long id) throws EntityNotFoundException, ServiceException {
        ThemeDao dao = new ThemeDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            Optional<Theme> theme = dao.findById(id);
            transaction.commit();
            if (theme.isEmpty()) {
                String message = "Theme with id %d not found".formatted(id);
                log.warning(message);
                throw new EntityNotFoundException(message);
            }
            log.info(() -> "Theme with id %d found successfully".formatted(id));
            return theme.get();
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot find theme with id %d: %s".formatted(id, ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public Theme findByName(String name) throws EntityNotFoundException, ServiceException {
        ThemeDao dao = new ThemeDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            Optional<Theme> theme = dao.findByName(name);
            transaction.commit();
            if (theme.isEmpty()) {
                String message = "Theme '%s' found".formatted(name);
                log.warning(message);
                throw new EntityNotFoundException(message);
            }
            log.info(() -> "Theme '%s' found successfully".formatted(name));
            return theme.get();
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot find theme '%s': %s".formatted(name, ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public Theme update(Theme target, Theme source) throws ServiceException {
        ThemeDao dao = new ThemeDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            if (dao.existsByName(source.getName()) && !target.getName().equals(source.getName())) {
                transaction.closeTransaction();
                String message = "Name '%s' is already taken".formatted(source.getName());
                log.warning(() -> "Cannot update theme '%s': %s".formatted(target.getName(), message));
                throw new EntityExistsException(message);
            }
            String oldName = target.getName();
            Theme updated = dao.update(target, source);
            transaction.commit();
            log.info(
                    () -> "Theme '%s' updated successfully to '%s'".formatted(oldName, source.getName())
            );
            return updated;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(
                    () -> "Cannot update theme '%s': %s".formatted(target.getName(), ex.getMessage())
            );
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public void delete(Theme theme) throws ServiceException {
        ThemeDao dao = new ThemeDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        String name = theme.getName();
        try {
            dao.delete(theme);
            transaction.commit();
            log.info(() -> "Theme '%s' deleted successfully".formatted(name));
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.severe(() -> "Cannot delete theme '%s': %s".formatted(name, ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }
}
