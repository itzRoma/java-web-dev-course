package com.itzroma.kpi.semester5.courseplatform.dao;

import com.itzroma.kpi.semester5.courseplatform.model.Entity;

import java.sql.Connection;

public interface AbstractDao<ID, T extends Entity<ID>> {
    void setConnection(Connection connection);
}
