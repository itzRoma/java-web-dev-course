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

public class StudentDaoImpl extends UserDaoImpl<Student> implements StudentDao {

    private static final String CREATE_STUDENT_QUERY = "INSERT INTO student (blocked, user_id) VALUES (?, ?)";

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
    public Student findById(Long id) throws UnsuccessfulOperationException {
        return null;
    }

    @Override
    public List<Student> findAll() throws UnsuccessfulOperationException {
        return Collections.emptyList();
    }

    @Override
    public Student update(Student target, Student source) throws UnsuccessfulOperationException {
        return null;
    }
}
