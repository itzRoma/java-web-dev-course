package com.itzroma.kpi.semester5.courseplatform.dao.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.StudentDao;
import com.itzroma.kpi.semester5.courseplatform.db.DBUtils;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudentDaoImpl extends UserDaoImpl<Student> implements StudentDao {

    public StudentDaoImpl() {
        super(Student.class);
    }

    private static final String CREATE_STUDENT_QUERY = "INSERT INTO student (blocked, user_id) VALUES (?, ?)";

    private static final String FIND_STUDENT_BY_USER_ID_QUERY = "SELECT * FROM student WHERE user_id = ?";

    @Override
    public Student create(Student entity) throws UnsuccessfulOperationException {
        super.create(entity);

        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(CREATE_STUDENT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            ps.setBoolean(++i, entity.getBlocked());
            ps.setLong(++i, entity.getUserId());

            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    entity.setStudentId(rs.getLong(1));
                    return entity;
                }
            }
            throw new SQLException("Cannot create new student and return student id");
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public Optional<Student> findById(Long id) throws UnsuccessfulOperationException {
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() throws UnsuccessfulOperationException {
        return Collections.emptyList();
    }

    @Override
    public Student update(Student target, Student source) throws UnsuccessfulOperationException {
        return null;
    }

    @Override
    public Optional<Student> findByEmail(String email) throws UnsuccessfulOperationException {
        Optional<Student> student = super.findByEmail(email);
        if (student.isEmpty()) return Optional.empty();

        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_STUDENT_BY_USER_ID_QUERY)) {
            int i = 0;
            ps.setLong(++i, student.get().getUserId());

            i = 0;
            rs = ps.executeQuery();
            while (rs.next()) {
                student.get().setStudentId(rs.getLong(++i));
                student.get().setBlocked(rs.getBoolean(++i));
                student.get().setUserId(rs.getLong(++i));
            }
            return student;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }
}
