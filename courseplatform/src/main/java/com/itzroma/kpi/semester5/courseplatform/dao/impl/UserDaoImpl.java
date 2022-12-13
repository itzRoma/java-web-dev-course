package com.itzroma.kpi.semester5.courseplatform.dao.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.UserDao;
import com.itzroma.kpi.semester5.courseplatform.db.DBUtils;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Role;
import com.itzroma.kpi.semester5.courseplatform.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

public abstract class UserDaoImpl<T extends User> extends CrudDaoImpl<Long, T> implements UserDao<T> {
    private final Class<T> entityClass;

    protected UserDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    private static final String CREATE_USER_QUERY =
            "INSERT INTO user (first_name, last_name, email, password, role, registration_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String USER_EXISTS_BY_EMAIL_QUERY = "SELECT COUNT(id) FROM user WHERE email = ?";

    private static final String USER_EXISTS_BY_EMAIL_AND_PASSWORD_QUERY =
            "SELECT COUNT(id) FROM user WHERE email = ? AND password = ?";

    private static final String FIND_USER_BY_EMAIL_QUERY = "SELECT * FROM user WHERE email = ?";

    private static final String GET_USER_ROLE_BY_EMAIL_QUERY = "SELECT role FROM user WHERE email = ?";

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

    @Override
    public boolean existsByEmailAndPassword(String email, String password) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(USER_EXISTS_BY_EMAIL_AND_PASSWORD_QUERY)) {
            int i = 0;
            ps.setString(++i, email);
            ps.setString(++i, password);

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

    @Override
    public Optional<T> findByEmail(String email) throws UnsuccessfulOperationException {
        T entity = instantiateNewEntity();
        if (entity == null) return Optional.empty();

        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_USER_BY_EMAIL_QUERY)) {
            int i = 0;
            ps.setString(++i, email);

            i = 0;
            rs = ps.executeQuery();
            while (rs.next()) {
                entity.setUserId(rs.getLong(++i));
                entity.setFirstName(rs.getString(++i));
                entity.setLastName(rs.getString(++i));
                entity.setEmail(rs.getString(++i));
                entity.setPassword(rs.getString(++i));
                entity.setRole(Role.valueOf(rs.getString(++i)));
                entity.setRegistrationDate(LocalDateTime.ofInstant(
                        rs.getDate(++i).toInstant(), ZoneId.systemDefault()
                ));
            }
            return Optional.of(entity);
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    private T instantiateNewEntity() {
        try {
            return entityClass.getConstructor().newInstance();
        } catch (ReflectiveOperationException ex) {
            return null;
        }
    }

    @Override
    public Role getRoleByEmail(String email) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(GET_USER_ROLE_BY_EMAIL_QUERY)) {
            int i = 0;
            ps.setString(++i, email);

            i = 0;
            rs = ps.executeQuery();
            if (rs.next()) {
                return Role.valueOf(rs.getString(++i));
            }
            throw new SQLException("Cannot get user role by email");
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }
}
