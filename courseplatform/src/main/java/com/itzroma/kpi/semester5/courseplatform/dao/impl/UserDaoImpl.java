package com.itzroma.kpi.semester5.courseplatform.dao.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.UserDao;
import com.itzroma.kpi.semester5.courseplatform.db.DBUtils;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.User;

import java.sql.*;

public abstract class UserDaoImpl<T extends User> extends CrudDaoImpl<Long, T> implements UserDao<T> {

    private static final String CREATE_USER_QUERY =
            "INSERT INTO user (first_name, last_name, email, password, role, registration_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String USER_EXISTS_BY_EMAIL_QUERY = "SELECT COUNT(id) FROM user WHERE email = ?";

    @Override
    public T create(T entity) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            ps.setString(++i, entity.getFirstName());
            ps.setString(++i, entity.getLastName());
            ps.setString(++i, entity.getEmail());
            ps.setString(++i, entity.getPassword());
            ps.setString(++i, entity.getRole().name());
            ps.setDate(++i, Date.valueOf(entity.getRegistrationDate().toLocalDate()));

            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    entity.setUserId(rs.getLong(1));
                    return entity;
                }
            }
            throw new SQLException("Cannot create new user and return user id");
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public boolean existsByEmail(String email) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(USER_EXISTS_BY_EMAIL_QUERY)) {
            int i = 0;
            ps.setString(++i, email);

            i = 0;
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(++i) != 0;
            }
            return false;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }
}
