package com.itzroma.kpi.semester5.courseplatform.dao.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.CrudDao;
import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Entity;
import lombok.AllArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@AllArgsConstructor
public abstract class CrudDaoImpl<ID, T extends Entity<ID>> extends AbstractDaoImpl<ID, T> implements CrudDao<ID, T> {

    private static final String DELETE_QUERY = "DELETE FROM ? WHERE id = ?";

    private String extractEntityTableName(T entity) {
        return entity.getClass().getSimpleName().replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

    @Override
    public void delete(T entity) throws UnsuccessfulOperationException {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_QUERY)) {
            int i = 0;
            ps.setString(++i, extractEntityTableName(entity));
            ps.setObject(++i, entity.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new UnsuccessfulOperationException(ex);
        }
    }
}
