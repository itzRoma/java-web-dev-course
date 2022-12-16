package com.itzroma.kpi.semester5.courseplatform.security;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.command.auth.AuthCommandFactory;
import com.itzroma.kpi.semester5.courseplatform.view.ViewDispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AuthServlet", value = "/auth/*")
public class AuthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Command command = new AuthCommandFactory(request, response).defineCommand();
        new ViewDispatcher(command.execute(), request, response).dispatch();
    }
}
