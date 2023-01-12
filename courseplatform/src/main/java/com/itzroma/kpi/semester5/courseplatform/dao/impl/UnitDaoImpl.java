package com.itzroma.kpi.semester5.courseplatform.dao.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.UnitDao;
import com.itzroma.kpi.semester5.courseplatform.db.DBUtils;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Unit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UnitDaoImpl extends CrudDaoImpl<Long, Unit> implements UnitDao {

    private static final String CREATE_UNIT_QUERY = "INSERT INTO unit (title, course_id) VALUES (?, ?)";

    private static final String FIND_UNIT_BY_ID = "SELECT * FROM unit WHERE id = ?";

    private static final String FIND_MANY_UNITS = "SELECT * FROM unit ORDER BY id DESC LIMIT ?";

    private static final String FIND_ALL_UNITS = "SELECT * FROM unit";

    private static final String UPDATE_UNIT = "UPDATE unit SET title = ? WHERE id = ?";

    private static final String EXISTS_BY_TITLE_AND_COURSE_ID =
            "SELECT COUNT(*) FROM unit WHERE title = ? AND course_id = ?";

    private static final String FIND_ALL_UNITS_BY_COURSE_ID = "SELECT * FROM unit WHERE course_id = ?";

    @Override
    public Unit create(Unit entity) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(
                CREATE_UNIT_QUERY, Statement.RETURN_GENERATED_KEYS
        )) {
            int i = 0;
            ps.setString(++i, entity.getTitle());
            ps.setLong(++i, entity.getCourseId());

            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    entity.setId(rs.getLong(1));
                    return entity;
                }
            }
            throw new SQLException("Cannot create new unit and return unit id");
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public Optional<Unit> findById(Long id) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_UNIT_BY_ID)) {
            int i = 0;
            ps.setLong(++i, id);

            i = 0;
            rs = ps.executeQuery();
            Unit unit = new Unit();
            while (rs.next()) {
                extractUnitFromResultSet(unit, rs, i);
            }
            return unit.getId() == null ? Optional.empty() : Optional.of(unit);
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    private void extractUnitFromResultSet(Unit unit, ResultSet rs, int i) throws SQLException {
        unit.setId(rs.getLong(++i));
        unit.setTitle(rs.getString(++i));
        unit.setCourseId(rs.getLong(++i));
    }

    @Override
    public List<Unit> findMany(int quantity) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        List<Unit> res = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(FIND_MANY_UNITS)) {
            int i = 0;
            ps.setInt(++i, quantity);

            i = 0;
            rs = ps.executeQuery();
            while (rs.next()) {
                Unit unit = new Unit();
                extractUnitFromResultSet(unit, rs, i);
                res.add(unit);
            }
            return res;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public List<Unit> findAll() throws UnsuccessfulOperationException {
        ResultSet rs = null;
        List<Unit> res = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_UNITS)) {
            int i = 0;
            rs = ps.executeQuery();
            while (rs.next()) {
                Unit unit = new Unit();
                extractUnitFromResultSet(unit, rs, i);
                res.add(unit);
            }
            return res;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public Unit update(Unit target, Unit source) throws UnsuccessfulOperationException {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_UNIT)) {
            int i = 0;
            ps.setString(++i, source.getTitle());
            ps.setLong(++i, target.getId());

            SQLException exception = new SQLException("Cannot update unit with id %d".formatted(target.getId()));
            if (ps.executeUpdate() == 0) {
                throw exception;
            }
            return findById(target.getId()).orElseThrow(() -> exception);
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        }
    }

    @Override
    public boolean existsByTitleAndCourseId(String title, Long courseId) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(EXISTS_BY_TITLE_AND_COURSE_ID)) {
            int i = 0;
            ps.setString(++i, title);
            ps.setLong(++i, courseId);

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
    public List<Unit> findAllByCourseId(Long courseId) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        List<Unit> res = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_UNITS_BY_COURSE_ID)) {
            int i = 0;
            ps.setLong(++i, courseId);

            i = 0;
            rs = ps.executeQuery();
            while (rs.next()) {
                Unit unit = new Unit();
                extractUnitFromResultSet(unit, rs, i);
                res.add(unit);
            }
            return res;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }
}
