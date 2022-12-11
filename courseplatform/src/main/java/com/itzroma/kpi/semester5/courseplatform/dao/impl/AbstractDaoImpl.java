package com.itzroma.kpi.semester5.courseplatform.dao.impl;

import com.itzroma.kpi.semester5.courseplatform.dao.AbstractDao;
import com.itzroma.kpi.semester5.courseplatform.db.ConnectionProxy;
import com.itzroma.kpi.semester5.courseplatform.model.Entity;

import java.sql.Connection;

public abstract class AbstractDaoImpl<ID, T extends Entity<ID>> implements AbstractDao<ID, T> {
    protected Connection connection;

    @Override
    public void setConnection(Connection connection) {
        if (!(connection instanceof ConnectionProxy)) {
            throw new IllegalArgumentException("'Wild' connection provided");
        }
        this.connection = connection;
    }
}
