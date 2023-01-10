package com.itzroma.kpi.semester5.courseplatform.db;

import com.itzroma.kpi.semester5.courseplatform.dao.AbstractDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Logger;

public class Transaction {
    private final Connection connection;

    private static final Logger log = Logger.getLogger(Transaction.class.getName());

    public Transaction() {
        connection = MySQLConnectionPool.INSTANCE.getConnection();
    }

    public final void openTransaction(AbstractDao dao, AbstractDao... daos) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            log.severe(ex.getMessage());
        }

        dao.setConnection(connection);
        Arrays.stream(daos).forEach(d -> d.setConnection(connection));
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException ex) {
            log.severe(ex.getMessage());
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            log.severe(ex.getMessage());
        }
    }

    public void closeTransaction() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            log.severe(ex.getMessage());
        } finally {
            DBUtils.close(connection);
        }
    }
}
