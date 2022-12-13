package com.itzroma.kpi.semester5.courseplatform.security;

import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Student;
import com.itzroma.kpi.semester5.courseplatform.service.StudentService;
import com.itzroma.kpi.semester5.courseplatform.service.impl.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AuthServlet", value = "/auth/*")
public class AuthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("redirectTo", request.getParameter("redirect-to"));
        switch (extractAction(request)) {
            case "sign-up" -> request.getRequestDispatcher("/sign-up.jsp").forward(request, response);
            case "sign-in" -> request.getRequestDispatcher("/sign-in.jsp").forward(request, response);
            case "sign-out" -> signOut(request, response); // TODO: process sign-out in POST request
            default -> request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void signOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("email");
        session.removeAttribute("role");
        session.invalidate();

        String redirectTo = request.getParameter("redirect-to");
        if (redirectTo != null && !redirectTo.isBlank()) {
            response.sendRedirect(redirectTo);
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    private String extractAction(HttpServletRequest request) {
        String[] parts = request.getRequestURI().split("/");
        return parts[parts.length - 1];
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailParameter = request.getParameter("email");
        String passwordParameter = request.getParameter("password");
        String redirectTo = request.getParameter("redirect-to");

        StudentService service = new StudentServiceImpl();
        switch (extractAction(request)) {
            case "sign-up" -> {
                try {
                    Student student = service.register(new Student(
                            request.getParameter("first_name"),
                            request.getParameter("last_name"),
                            emailParameter,
                            passwordParameter
                    ));

                    HttpSession session = request.getSession();
                    session.setAttribute("email", student.getEmail());
                    session.setAttribute("role", student.getRole());

                    if (redirectTo != null && !redirectTo.isBlank()) {
                        response.sendRedirect(redirectTo);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/profile");
                    }
                    break;
                } catch (EntityExistsException ex) {
                    request.setAttribute("emailMessage", ex.getMessage());
                } catch (ServiceException ex) {
                    request.setAttribute("errorMessage", ex.getMessage());
                }
                request.getRequestDispatcher("/sign-up.jsp").forward(request, response);
            }
            case "sign-in" -> {
                try {
                    if (!service.existsByEmail(emailParameter)) {
                        request.setAttribute("emailMessage", "Invalid email");
                        request.getRequestDispatcher("/sign-in.jsp").forward(request, response);
                        break;
                    }
                    if (!service.existsByEmailAndPassword(emailParameter, passwordParameter)) {
                        request.setAttribute("passwordMessage", "Invalid password");
                        request.getRequestDispatcher("/sign-in.jsp").forward(request, response);
                        break;
                    }

                    HttpSession session = request.getSession();
                    session.setAttribute("email", emailParameter);
                    session.setAttribute("role", service.getRoleByEmail(emailParameter));

                    if (redirectTo != null && !redirectTo.isBlank()) {
                        response.sendRedirect(redirectTo);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/profile");
                    }
                    break;
                } catch (ServiceException ex) {
                    request.setAttribute("errorMessage", ex.getMessage());
                }
                request.getRequestDispatcher("/sign-in.jsp").forward(request, response);
            }
            default -> request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
