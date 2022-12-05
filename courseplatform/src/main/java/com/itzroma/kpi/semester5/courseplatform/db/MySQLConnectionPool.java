package com.itzroma.kpi.semester5.courseplatform.db;

import com.itzroma.kpi.semester5.courseplatform.exception.ConfigurationException;
import com.itzroma.kpi.semester5.courseplatform.exception.InitializationException;
import com.itzroma.kpi.semester5.courseplatform.exception.PooledConnectionException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

public enum MySQLConnectionPool implements ConnectionPool {
    INSTANCE;

    private DataSource dataSource;
    private int defaultPoolSize;

    private final BlockingQueue<ConnectionProxy> freeConnections;
    private final Queue<ConnectionProxy> occupiedConnections;

    private final Logger log = Logger.getLogger(MySQLConnectionPool.class.getName());

    MySQLConnectionPool() {
        configureDataSource();
        freeConnections = new LinkedBlockingDeque<>(defaultPoolSize);
        initializeConnections();
        occupiedConnections = new ArrayDeque<>();
    }

    private void configureDataSource() {
        log.info("Configuring data source...");
        long start = System.currentTimeMillis();

        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/courseplatform-cp");
            defaultPoolSize = 32; // TODO: think how to replace this 'magic' number
        } catch (NamingException ex) {
            String message = "Cannot configure data source: %s".formatted(ex.getMessage());
            log.severe(message);
            throw new ConfigurationException(message, ex);
        }

        long total = System.currentTimeMillis() - start;
        log.info(() -> "Data source configured in %d ms".formatted(total));
    }

    private void initializeConnections() {
        log.info("Initializing connections...");
        long start = System.currentTimeMillis();

        for (int i = 0; i < defaultPoolSize; i++) {
            try {
                freeConnections.offer(new ConnectionProxy(dataSource.getConnection()));
            } catch (SQLException ex) {
                String message = "Cannot initialize connections in pool: %s".formatted(ex.getMessage());
                log.severe(message);
                throw new InitializationException(message, ex);
            }
        }

        long total = System.currentTimeMillis() - start;
        log.info(() -> "%d connections initialized in %d ms".formatted(freeConnections.size(), total));
    }

    @Override
    public Connection getConnection() {
        ConnectionProxy connection;
        try {
            connection = freeConnections.take();
            occupiedConnections.offer(connection);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();

            String message = "Cannot get connection from pool: %s".formatted(ex.getMessage());
            log.severe(message);
            throw new PooledConnectionException(message, ex);
        }
        return connection;
    }

    @Override
    public void releaseConnection(Connection connection) {
        if (!(connection instanceof ConnectionProxy)) {
            throw new IllegalArgumentException("Invalid connection type provided");
        }

        try {
            occupiedConnections.remove(connection);
            freeConnections.offer((ConnectionProxy) connection);
        } catch (Exception ex) {
            String message = "Cannot release connection to pool: %s".formatted(ex);
            log.severe(message);
            throw new PooledConnectionException(message, ex);
        }
    }

    @Override
    public void shutdown() {
        log.info("Shutting down connection pool...");
        long start = System.currentTimeMillis();

        freeConnections.forEach(connectionProxy -> {
            try {
                connectionProxy.reallyClose();
            } catch (SQLException ex) {
                String message = "Cannot shut down connection pool: %s".formatted(ex.getMessage());
                log.severe(message);
                throw new ConfigurationException(message, ex);
            }
        });

        long total = System.currentTimeMillis() - start;
        log.info(() -> "Connection pool shut down in %d ms".formatted(total));
    }
}
