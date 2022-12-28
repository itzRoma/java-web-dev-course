package com.itzroma.kpi.semester5.courseplatform.dao.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.UserDao;
import com.itzroma.kpi.semester5.courseplatform.db.DBUtils;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Role;
import com.itzroma.kpi.semester5.courseplatform.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class UserDaoImpl<T extends User> extends CrudDaoImpl<Long, T> implements UserDao<T> {
    private final Class<T> entityClass;

    protected UserDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    private static final String CREATE_USER_QUERY =
            "INSERT INTO user (first_name, last_name, email, password, role, registration_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String USER_EXISTS_BY_EMAIL_QUERY = "SELECT COUNT(id) FROM user WHERE email = ? AND role = ?";

    private static final String USER_EXISTS_BY_EMAIL_AND_PASSWORD_QUERY =
            "SELECT COUNT(id) FROM user WHERE email = ? AND password = ? AND role = ?";

    private static final String FIND_USER_BY_EMAIL_QUERY = "SELECT * FROM user WHERE email = ? AND role = ?";

    private static final String GET_USER_ROLE_BY_EMAIL_QUERY = "SELECT role FROM user WHERE email = ?";

    private static final String UPDATE_USER_BY_USER_ID_QUERY =
            "UPDATE user SET first_name = ?, last_name = ?, email = ? WHERE id = ?";

    private static final String GET_USER_EMAIL_BY_USER_ID_QUERY = "SELECT email FROM user WHERE id = ?";

    private static final String FIND_MANY_USERS = "SELECT * FROM user WHERE role = ? ORDER BY id DESC LIMIT ?";

    private static final String FIND_ALL_USERS = "SELECT * FROM user WHERE role = ?";

    @Override
    public T create(T entity) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(
                CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS
        )) {
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
    public Optional<T> findById(Long aLong) throws UnsuccessfulOperationException {
        return Optional.empty();
    }

    protected List<T> findManyUsers(int quantity, Role role) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        List<T> res = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(FIND_MANY_USERS)) {
            int i = 0;
            ps.setString(++i, role.name());
            ps.setInt(++i, quantity);

            i = 0;
            rs = ps.executeQuery();
            while (rs.next()) {
                T user = instantiateNewEntity();
                if (user == null) return Collections.emptyList();

                extractUserFromResultSet(user, rs, i);
                res.add(user);
            }
            return res;
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

    private void extractUserFromResultSet(T user, ResultSet rs, int i) throws SQLException {
        user.setUserId(rs.getLong(++i));
        user.setFirstName(rs.getString(++i));
        user.setLastName(rs.getString(++i));
        user.setEmail(rs.getString(++i));
        user.setPassword(rs.getString(++i));
        user.setRole(Role.valueOf(rs.getString(++i)));
        user.setRegistrationDate(rs.getTimestamp(++i).toLocalDateTime());
    }

    protected List<T> findAllUsers(Role role) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        List<T> res = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_USERS)) {
            int i = 0;
            ps.setString(++i, role.name());

            i = 0;
            rs = ps.executeQuery();
            while (rs.next()) {
                T user = instantiateNewEntity();
                if (user == null) return Collections.emptyList();

                extractUserFromResultSet(user, rs, i);
                res.add(user);
            }
            return res;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public T update(T target, T source) throws UnsuccessfulOperationException {
        return null;
    }

    protected boolean existsByEmail(String email, Role role) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(USER_EXISTS_BY_EMAIL_QUERY)) {
            int i = 0;
            ps.setString(++i, email);
            ps.setString(++i, role.name());

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

    protected boolean existsByEmailAndPassword(String email, String password, Role role)
            throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(USER_EXISTS_BY_EMAIL_AND_PASSWORD_QUERY)) {
            int i = 0;
            ps.setString(++i, email);
            ps.setString(++i, password);
            ps.setString(++i, role.name());

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

    protected Optional<T> findUserByEmail(String email, Role role) throws UnsuccessfulOperationException {
        T entity = instantiateNewEntity();
        if (entity == null) return Optional.empty();

        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_USER_BY_EMAIL_QUERY)) {
            int i = 0;
            ps.setString(++i, email);
            ps.setString(++i, role.name());

            i = 0;
            rs = ps.executeQuery();
            while (rs.next()) {
                extractUserFromResultSet(entity, rs, i);
            }
            return Optional.of(entity);
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public Role getUserRoleByEmail(String email) throws UnsuccessfulOperationException {
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

    @Override
    public T updateByUserId(Long targetId, User source) throws UnsuccessfulOperationException {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_USER_BY_USER_ID_QUERY)) {
            int i = 0;
            ps.setString(++i, source.getFirstName());
            ps.setString(++i, source.getLastName());
            ps.setString(++i, source.getEmail());
            ps.setLong(++i, targetId);

            SQLException exception = new SQLException("Cannot update user with id %d".formatted(targetId));
            if (ps.executeUpdate() == 0) {
                throw exception;
            }
            return findByEmail(source.getEmail()).orElseThrow(() -> exception);
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        }
    }

    @Override
    public String getEmailByUserId(Long userId) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(GET_USER_EMAIL_BY_USER_ID_QUERY)) {
            int i = 0;
            ps.setLong(++i, userId);

            i = 0;
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(++i);
            }
            throw new SQLException("Cannot get email by user id %d".formatted(userId));
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }
}
