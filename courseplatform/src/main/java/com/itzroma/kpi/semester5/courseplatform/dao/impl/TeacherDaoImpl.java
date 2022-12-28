package com.itzroma.kpi.semester5.courseplatform.dao.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.TeacherDao;
import com.itzroma.kpi.semester5.courseplatform.db.DBUtils;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Role;
import com.itzroma.kpi.semester5.courseplatform.model.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class TeacherDaoImpl extends UserDaoImpl<Teacher> implements TeacherDao {
    public TeacherDaoImpl() {
        super(Teacher.class);
    }

    private static final String CREATE_TEACHER_QUERY = "INSERT INTO teacher (user_id) VALUES (?)";

    private static final String FIND_TEACHER_BY_USER_ID_QUERY = "SELECT * FROM teacher WHERE user_id = ?";

    private static final String FIND_MANY_TEACHERS = "SELECT * FROM teacher ORDER BY id DESC LIMIT ?";

    private static final String FIND_ALL_TEACHERS = "SELECT * FROM teacher";

    @Override
    public Teacher create(Teacher entity) throws UnsuccessfulOperationException {
        Teacher created = super.create(entity);

        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(
                CREATE_TEACHER_QUERY, Statement.RETURN_GENERATED_KEYS
        )) {
            int i = 0;
            ps.setLong(++i, entity.getUserId());

            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    created.setTeacherId(rs.getLong(1));
                    return created;
                }
            }
            throw new SQLException("Cannot create new teacher and return teacher id");
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public List<Teacher> findMany(int quantity) throws UnsuccessfulOperationException {
        List<Teacher> teachers = super.findManyUsers(quantity, Role.TEACHER);

        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_MANY_TEACHERS)) {
            int i = 0;
            ps.setInt(++i, quantity);

            rs = ps.executeQuery();
            int j = 0;
            while (rs.next()) {
                i = 0;
                teachers.get(j).setTeacherId(rs.getLong(++i));
                teachers.get(j).setUserId(rs.getLong(++i));
                j++;
            }
            return teachers;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public List<Teacher> findAll() throws UnsuccessfulOperationException {
        List<Teacher> teachers = super.findAllUsers(Role.TEACHER);

        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_TEACHERS)) {
            rs = ps.executeQuery();
            int i;
            int j = 0;
            while (rs.next()) {
                i = 0;
                teachers.get(j).setTeacherId(rs.getLong(++i));
                teachers.get(j).setUserId(rs.getLong(++i));
                j++;
            }
            return teachers;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public boolean existsByEmail(String email) throws UnsuccessfulOperationException {
        return existsByEmail(email, Role.TEACHER);
    }

    @Override
    public boolean existsByEmailAndPassword(String email, String password) throws UnsuccessfulOperationException {
        return existsByEmailAndPassword(email, password, Role.TEACHER);
    }

    @Override
    public Optional<Teacher> findByEmail(String email) throws UnsuccessfulOperationException {
        Optional<Teacher> teacher = super.findUserByEmail(email, Role.TEACHER);
        if (teacher.isEmpty()) return Optional.empty();

        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_TEACHER_BY_USER_ID_QUERY)) {
            int i = 0;
            ps.setLong(++i, teacher.get().getUserId());

            i = 0;
            rs = ps.executeQuery();
            while (rs.next()) {
                teacher.get().setTeacherId(rs.getLong(++i));
                teacher.get().setUserId(rs.getLong(++i));
            }
            return teacher;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }
}
