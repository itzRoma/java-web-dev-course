package com.itzroma.kpi.semester5.courseplatform.db;

import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection();

    void releaseConnection(Connection connection);

    void shutdown();
}

