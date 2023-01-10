package com.itzroma.kpi.semester5.courseplatform.dao.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.ThemeDao;
import com.itzroma.kpi.semester5.courseplatform.db.DBUtils;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Theme;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThemeDaoImpl extends CrudDaoImpl<Long, Theme> implements ThemeDao {

    private static final String CREATE_THEME_QUERY = "INSERT INTO theme (name) VALUES (?)";

    private static final String FIND_THEME_BY_ID = "SELECT * FROM theme WHERE id = ?";

    private static final String GET_NUMBER_OF_USES = "SELECT COUNT(*) FROM courses_themes WHERE theme_id = ?";

    private static final String FIND_MANY_THEMES = "SELECT * FROM theme ORDER BY id DESC LIMIT ?";

    private static final String FIND_ALL_THEMES = "SELECT * FROM theme ORDER BY id ASC";

    private static final String UPDATE_THEME = "UPDATE theme SET name = ? WHERE id = ?";

    private static final String FIND_THEME_BY_NAME = "SELECT * FROM theme WHERE name = ?";

    private static final String THEME_EXISTS_BY_NAME = "SELECT COUNT(*) FROM theme WHERE name = ?";

    private static final String GET_THEMES_BY_COURSE_ID = "SELECT theme_id FROM courses_themes WHERE course_id = ?";

    @Override
    public Theme create(Theme entity) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(
                CREATE_THEME_QUERY, Statement.RETURN_GENERATED_KEYS
        )) {
            int i = 0;
            ps.setString(++i, entity.getName());

            i = 0;
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    entity.setId(rs.getLong(++i));
                    return entity;
                }
            }
            throw new SQLException("Cannot create new theme and return theme id");
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public Optional<Theme> findById(Long id) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_THEME_BY_ID)) {
            int i = 0;
            ps.setLong(++i, id);

            i = 0;
            rs = ps.executeQuery();
            Theme theme = new Theme();
            while (rs.next()) {
                extractThemeFromResultSet(theme, rs, i);
            }
            theme.setNumberOfUses(getNumberOfUses(theme.getId()));
            return theme.getId() == null ? Optional.empty() : Optional.of(theme);
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    private void extractThemeFromResultSet(Theme theme, ResultSet rs, int i) throws SQLException {
        theme.setId(rs.getLong(++i));
        theme.setName(rs.getString(++i));
    }

    @Override
    public List<Theme> findMany(int quantity) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        List<Theme> res = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(FIND_MANY_THEMES)) {
            int i = 0;
            ps.setInt(++i, quantity);

            i = 0;
            rs = ps.executeQuery();
            while (rs.next()) {
                Theme theme = new Theme();
                extractThemeFromResultSet(theme, rs, i);
                theme.setNumberOfUses(getNumberOfUses(theme.getId()));
                res.add(theme);
            }
            return res;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public List<Theme> findAll() throws UnsuccessfulOperationException {
        ResultSet rs = null;
        List<Theme> res = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_THEMES)) {
            int i = 0;
            rs = ps.executeQuery();
            while (rs.next()) {
                Theme theme = new Theme();
                extractThemeFromResultSet(theme, rs, i);
                theme.setNumberOfUses(getNumberOfUses(theme.getId()));
                res.add(theme);
            }
            return res;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public Theme update(Theme target, Theme source) throws UnsuccessfulOperationException {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_THEME)) {
            int i = 0;
            ps.setString(++i, source.getName());
            ps.setLong(++i, target.getId());

            SQLException exception = new SQLException("Cannot update theme with id %d".formatted(target.getId()));
            if (ps.executeUpdate() == 0) {
                throw exception;
            }
            return findByName(source.getName()).orElseThrow(() -> exception);
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        }
    }

    @Override
    public Optional<Theme> findByName(String name) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(FIND_THEME_BY_NAME)) {
            int i = 0;
            ps.setString(++i, URLDecoder.decode(name, StandardCharsets.UTF_8));

            i = 0;
            rs = ps.executeQuery();
            Theme theme = new Theme();
            while (rs.next()) {
                extractThemeFromResultSet(theme, rs, i);
            }
            theme.setNumberOfUses(getNumberOfUses(theme.getId()));
            return theme.getId() == null ? Optional.empty() : Optional.of(theme);
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public int getNumberOfUses(long themeId) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(GET_NUMBER_OF_USES)) {
            int i = 0;
            ps.setLong(++i, themeId);

            i = 0;
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(++i);
            }
            throw new SQLException("Cannot get number of uses for theme with id %d".formatted(themeId));
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }

    @Override
    public boolean existsByName(String name) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(THEME_EXISTS_BY_NAME)) {
            int i = 0;
            ps.setString(++i, name);

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
    public List<Theme> getByCourseId(Long courseId) throws UnsuccessfulOperationException {
        ResultSet rs = null;
        List<Theme> themes = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_THEMES_BY_COURSE_ID)) {
            int i = 0;
            ps.setLong(++i, courseId);

            rs = ps.executeQuery();
            while (rs.next()) {
                i = 0;
                themes.add(findById(rs.getLong(++i)).get());
            }
            return themes;
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        } finally {
            DBUtils.close(rs);
        }
    }
}
