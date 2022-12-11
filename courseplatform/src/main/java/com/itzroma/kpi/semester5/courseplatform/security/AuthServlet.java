package com.itzroma.kpi.semester5.courseplatform.security;

import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Student;
import com.itzroma.kpi.semester5.courseplatform.service.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AuthenticationServlet", value = "/auth/sign-up")
public class AuthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/sign-up.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();

        StudentServiceImpl service = new StudentServiceImpl();
        try {
            Student student = service.create(new Student(
                    request.getParameter("first_name"),
                    request.getParameter("last_name"),
                    request.getParameter("email"),
                    request.getParameter("password")
            ));
            writer.println("New user created: %s".formatted(student));
        } catch (ServiceException | EntityExistsException ex) {
            writer.println(ex.getMessage());
        }
    }
}
