package com.itzroma.kpi.semester5.courseplatform.db;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "DBConnectionTesterServlet", value = "/test-db-connection")
public class DBConnectionTesterServlet extends HttpServlet {
    private ConnectionPool connectionPool;

    @Override
    public void init() {
        connectionPool = MySQLConnectionPool.INSTANCE;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        int quantity;
        try {
            quantity = Integer.parseInt(request.getParameter("q"));
        } catch (NumberFormatException ex) {
            quantity = 0;
        }

        quantity = quantity <= 0 ? 1 : quantity;

        for (int i = 0; i < quantity; i++) {
            try (Connection connection = connectionPool.getConnection()) {
                out.printf("%d - %s%n", i + 1, connection);
            } catch (SQLException ex) {
                log("Cannot establish connection %d".formatted(i + 1));
            }
        }
    }

    @Override
    public void destroy() {
        connectionPool.shutdown();
    }
}