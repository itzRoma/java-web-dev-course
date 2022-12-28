package com.itzroma.kpi.semester5.courseplatform.dao.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.AdminDao;
import com.itzroma.kpi.semester5.courseplatform.db.DBUtils;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Admin;
import com.itzroma.kpi.semester5.courseplatform.model.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class AdminDaoImpl extends UserDaoImpl<Admin> implements AdminDao {
    public AdminDaoImpl() {
        super(Admin.class);
    }

    private static final String CREATE_ADMIN_QUERY = "INSERT INTO admin (user_id) VALUES (?)";

    private static final String FIND_ADMIN_BY_USER_ID_QUERY = "SELECT * FROM admin WHERE user_id = ?";

    private static final String FIND_MANY_ADMINS = "SELECT * FROM admin ORDER BY id DESC LIMIT ?";

    private static final String FIND_ALL_ADMINS = "SELECT * FROM admin";

    @Override
    public Admin create(Admin entity) throws UnsuccessfulOperationException {
        Admin created = super.create(entity);

        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(
                CREATE_ADMIN_QUERY, Statement.RETURN_GENERATED_KEYS
        )) {
            int i = 0;
            ps.setLong(++i, entity.getUserId());

            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    created.setAdminId(rs.getLong(1));
                    return created;
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
    public List<Admin> findMany(int quantity) throws UnsuccessfulOperationException {
        List<Admin> admins = super.findManyUsers(quantity, Role.ADMIN);

        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_MANY_ADMINS)) {
            int i = 0;
            ps.setInt(++i, quantity);

            rs = ps.executeQuery();
            int j = 0;
            while (rs.next()) {
                i = 0;
                admins.get(j).setAdminId(rs.getLong(++i));
                admins.get(j).setUserId(rs.getLong(++i));
                j++;
            }
            return admins;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public List<Admin> findAll() throws UnsuccessfulOperationException {
        List<Admin> admins = super.findAllUsers(Role.ADMIN);

        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_ADMINS)) {
            rs = ps.executeQuery();
            int i;
            int j = 0;
            while (rs.next()) {
                i = 0;
                admins.get(j).setAdminId(rs.getLong(++i));
                admins.get(j).setUserId(rs.getLong(++i));
                j++;
            }
            return admins;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public boolean existsByEmail(String email) throws UnsuccessfulOperationException {
        return existsByEmail(email, Role.ADMIN);
    }

    @Override
    public boolean existsByEmailAndPassword(String email, String password) throws UnsuccessfulOperationException {
        return existsByEmailAndPassword(email, password, Role.ADMIN);
    }

    @Override
    public Optional<Admin> findByEmail(String email) throws UnsuccessfulOperationException {
        Optional<Admin> admin = super.findUserByEmail(email, Role.ADMIN);
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
