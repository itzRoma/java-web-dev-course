package com.itzroma.kpi.semester5.courseplatform.dao.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.AdminDao;
import com.itzroma.kpi.semester5.courseplatform.db.DBUtils;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Admin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AdminDaoImpl extends UserDaoImpl<Admin> implements AdminDao {
    public AdminDaoImpl() {
        super(Admin.class);
    }

    private static final String CREATE_ADMIN_QUERY = "INSERT INTO admin (user_id) VALUES (?)";

    private static final String FIND_ADMIN_BY_USER_ID_QUERY = "SELECT * FROM admin WHERE user_id = ?";

    @Override
    public Admin create(Admin entity) throws UnsuccessfulOperationException {
        super.create(entity);

        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(CREATE_ADMIN_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            ps.setLong(++i, entity.getUserId());

            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    entity.setAdminId(rs.getLong(1));
                    return entity;
                }
            }
            throw new SQLException("Cannot create new admin and return admin id");
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public Optional<Admin> findById(Long aLong) throws UnsuccessfulOperationException {
        return Optional.empty();
    }

    @Override
    public List<Admin> findMany(int quantity) throws UnsuccessfulOperationException {
        return Collections.emptyList();
    }

    @Override
    public List<Admin> findAll() throws UnsuccessfulOperationException {
        return Collections.emptyList();
    }

    @Override
    public Admin update(Admin target, Admin source) throws UnsuccessfulOperationException {
        return null;
    }

    @Override
    public Optional<Admin> findByEmail(String email) throws UnsuccessfulOperationException {
        Optional<Admin> admin = super.findByEmail(email);
        if (admin.isEmpty()) return Optional.empty();

        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_ADMIN_BY_USER_ID_QUERY)) {
            int i = 0;
            ps.setLong(++i, admin.get().getUserId());

            i = 0;
            rs = ps.executeQuery();
            while (rs.next()) {
                admin.get().setAdminId(rs.getLong(++i));
                admin.get().setUserId(rs.getLong(++i));
            }
            return admin;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }
}
