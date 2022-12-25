package com.itzroma.kpi.semester5.courseplatform.dao.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.StudentDao;
import com.itzroma.kpi.semester5.courseplatform.db.DBUtils;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Role;
import com.itzroma.kpi.semester5.courseplatform.model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class StudentDaoImpl extends UserDaoImpl<Student> implements StudentDao {

    public StudentDaoImpl() {
        super(Student.class);
    }

    private static final String CREATE_STUDENT_QUERY = "INSERT INTO student (blocked, user_id) VALUES (?, ?)";

    private static final String FIND_STUDENT_BY_USER_ID_QUERY = "SELECT * FROM student WHERE user_id = ?";

    private static final String FIND_MANY_STUDENTS = "SELECT * FROM student LIMIT ?";

    private static final String FIND_ALL_STUDENTS = "SELECT * FROM student";

    private static final String TOGGLE_BLOCK = "UPDATE student SET blocked = ? WHERE id = ?";

    private static final String CHECK_IF_BLOCKED = "SELECT blocked FROM student WHERE user_id = ?";

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
        List<Student> students = super.findAll(Role.STUDENT);

        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_STUDENTS)) {
            rs = ps.executeQuery();
            int i;
            int j = 0;
            while (rs.next()) {
                i = 0;
                students.get(j).setStudentId(rs.getLong(++i));
                students.get(j).setBlocked(rs.getBoolean(++i));
                students.get(j).setUserId(rs.getLong(++i));
                j++;
            }
            return students;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
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

    @Override
    public List<Student> findMany(int quantity) throws UnsuccessfulOperationException {
        List<Student> students = super.findMany(quantity, Role.STUDENT);

        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_MANY_STUDENTS)) {
            int i = 0;
            ps.setInt(++i, quantity);

            rs = ps.executeQuery();
            int j = 0;
            while (rs.next()) {
                i = 0;
                students.get(j).setStudentId(rs.getLong(++i));
                students.get(j).setBlocked(rs.getBoolean(++i));
                students.get(j).setUserId(rs.getLong(++i));
                j++;
            }
            return students;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public void toggleBlock(Student student) throws UnsuccessfulOperationException {
        try (PreparedStatement ps = connection.prepareStatement(TOGGLE_BLOCK)) {
            int i = 0;
            ps.setBoolean(++i, !student.getBlocked());
            ps.setLong(++i, student.getStudentId());

            if (ps.executeUpdate() != 1) {
                throw new SQLException(
                        "Cannot toggle block for student with student id %d".formatted(student.getStudentId())
                );
            }
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        }
    }

    @Override
    public boolean isStudentBlocked(String email) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(CHECK_IF_BLOCKED)) {
            int i = 0;
            ps.setLong(++i, convertStudentEmailToUserId(email));

            i = 0;
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(++i);
            }
            throw new SQLException("Cannot check if student with email '%s' is blocked".formatted(email));
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    private long convertStudentEmailToUserId(String email) {
        String sql = "SELECT id FROM user WHERE email = ?";

        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 0;
            ps.setString(++i, email);

            i = 0;
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong(++i);
            }
            throw new SQLException("Cannot convert student email to user id");
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }
}
