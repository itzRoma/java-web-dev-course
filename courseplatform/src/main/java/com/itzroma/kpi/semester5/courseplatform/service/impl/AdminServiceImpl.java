package com.itzroma.kpi.semester5.courseplatform.service.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.AdminDao;
import com.itzroma.kpi.semester5.courseplatform.dao.impl.AdminDaoImpl;
import com.itzroma.kpi.semester5.courseplatform.db.Transaction;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityNotFoundException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Admin;
import com.itzroma.kpi.semester5.courseplatform.service.AdminService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class AdminServiceImpl extends UserServiceImpl<Admin> implements AdminService {
    private static final Logger log = Logger.getLogger(AdminServiceImpl.class.getName());

    public AdminServiceImpl() {
        super(Admin.class);
    }

    @Override
    public Admin register(Admin entity) throws EntityExistsException, ServiceException {
        AdminDao dao = new AdminDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            if (dao.existsByEmail(entity.getEmail())) {
                transaction.closeTransaction();
                String message = "Email is already taken";
                log.warning(() -> "Cannot register new admin: %s".formatted(message));
                throw new EntityExistsException(message);
            }
            Admin admin = dao.create(entity);
            transaction.commit();
            log.info(() -> "Registered new admin with admin id %s".formatted(admin.getAdminId()));
            return admin;
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.warning(() -> "Cannot register new admin: %s".formatted(ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public Admin findByEmail(String email) throws EntityNotFoundException, ServiceException {
        AdminDao dao = new AdminDaoImpl();
        Transaction transaction = new Transaction();
        transaction.openTransaction(dao);
        try {
            Optional<Admin> admin = dao.findByEmail(email);
            transaction.commit();
            if (admin.isEmpty()) {
                String message = "Admin with provided email does not exist";
                log.warning(message);
                throw new EntityNotFoundException(message);
            }
            return admin.get();
        } catch (UnsuccessfulOperationException ex) {
            transaction.rollback();
            log.warning(() -> "Cannot find admin by email: %s".formatted(ex.getMessage()));
            throw new ServiceException(ex);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public List<Admin> findMany(int quantity) throws ServiceException {
        throw new ServiceException("Not implemented");
    }

    @Override
    public List<Admin> findAll() throws ServiceException {
        return Collections.emptyList();
    }
}
