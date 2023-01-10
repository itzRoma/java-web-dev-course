package com.itzroma.kpi.semester5.courseplatform.dao.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.CourseDao;
import com.itzroma.kpi.semester5.courseplatform.db.DBUtils;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Course;
import com.itzroma.kpi.semester5.courseplatform.model.CourseStatus;
import com.itzroma.kpi.semester5.courseplatform.model.Theme;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDaoImpl extends CrudDaoImpl<Long, Course> implements CourseDao {

    private static final String CREATE_COURSE_QUERY =
            "INSERT INTO course (title, description, duration, starting_date, status) VALUES (?, ?, ?, ?, ?)";

    private static final String FIND_COURSE_BY_ID = "SELECT * FROM course WHERE id = ?";

    private static final String FIND_MANY_COURSES = "SELECT * FROM course ORDER BY id DESC LIMIT ?";

    private static final String FIND_ALL_COURSES = "SELECT * FROM course";

    private static final String ASSIGN_THEMES = "INSERT INTO courses_themes (course_id, theme_id) VALUES (?, ?)";

    @Override
    public Course create(Course entity) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(
                CREATE_COURSE_QUERY, Statement.RETURN_GENERATED_KEYS
        )) {
            int i = 0;
            ps.setString(++i, entity.getTitle());
            ps.setString(++i, entity.getDescription());
            ps.setInt(++i, entity.getDuration());
            ps.setTimestamp(++i, Timestamp.valueOf(entity.getStartingDate()));
            ps.setString(++i, CourseStatus.CREATED.name());

            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    long id = rs.getLong(1);
                    entity.setId(id);
                    assignThemes(id, entity.getThemes());
                    return entity;
                }
            }
            throw new SQLException("Cannot create new course and return course id");
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public Optional<Course> findById(Long id) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_COURSE_BY_ID)) {
            int i = 0;
            ps.setLong(++i, id);

            i = 0;
            rs = ps.executeQuery();
            Course course = new Course();
            while (rs.next()) {
                extractCourseFromResultSet(course, rs, i);
            }
            return course.getId() == null ? Optional.empty() : Optional.of(course);
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public List<Course> findMany(int quantity) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        List<Course> res = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(FIND_MANY_COURSES)) {
            int i = 0;
            ps.setInt(++i, quantity);

            i = 0;
            rs = ps.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                extractCourseFromResultSet(course, rs, i);
                res.add(course);
            }
            return res;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    private void extractCourseFromResultSet(Course course, ResultSet rs, int i) throws SQLException {
        course.setId(rs.getLong(++i));
        course.setTitle(rs.getString(++i));
        course.setDescription(rs.getString(++i));
        course.setDuration(rs.getInt(++i));
        course.setMinGrade(rs.getInt(++i));
        course.setMaxGrade(rs.getInt(++i));
        course.setStartingDate(rs.getTimestamp(++i).toLocalDateTime());
        course.setStatus(CourseStatus.valueOf(rs.getString(++i)));
    }

    @Override
    public List<Course> findAll() throws UnsuccessfulOperationException {
        ResultSet rs = null;
        List<Course> res = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_COURSES)) {
            int i = 0;
            rs = ps.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                extractCourseFromResultSet(course, rs, i);
                res.add(course);
            }
            return res;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public Course update(Course target, Course source) throws UnsuccessfulOperationException {
        return null;
    }

    private void assignThemes(Long courseId, List<Theme> themes) throws UnsuccessfulOperationException {
        try (PreparedStatement ps = connection.prepareStatement(ASSIGN_THEMES)) {
            for (Theme theme : themes) {
                int i = 0;
                ps.setLong(++i, courseId);
                ps.setLong(++i, theme.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        }
    }
}
