package com.itzroma.kpi.semester5.courseplatform.security;

import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Role;
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

    private static final String REDIRECT_TO_PARAMETER = "redirect-to";
    private static final String SIGN_UP_JSP_PATH = "/sign-up.jsp";
    private static final String SIGN_IN_JSP_PATH = "/sign-in.jsp";
    private static final String ERROR_JSP_PATH = "/error.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("redirectTo", request.getParameter(REDIRECT_TO_PARAMETER));
        switch (extractAction(request)) {
            case "sign-up" -> request.getRequestDispatcher(SIGN_UP_JSP_PATH).forward(request, response);
            case "sign-in" -> request.getRequestDispatcher(SIGN_IN_JSP_PATH).forward(request, response);
            default -> request.getRequestDispatcher(ERROR_JSP_PATH).forward(request, response);
        }
    }

    private static String extractAction(HttpServletRequest request) {
        String[] parts = request.getRequestURI().split("/");
        return parts[parts.length - 1];
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailParameter = request.getParameter("email");
        String passwordParameter = request.getParameter("password");
        String redirectTo = request.getParameter(REDIRECT_TO_PARAMETER);

        StudentService service = new StudentServiceImpl();
        request.setAttribute("redirectTo", redirectTo);
        switch (extractAction(request)) {
            case "sign-up" -> {
                try {
                    Student student = service.register(new Student(
                            request.getParameter("first_name"),
                            request.getParameter("last_name"),
                            emailParameter,
                            passwordParameter
                    ));
                    signInInSession(request, student.getEmail(), student.getRole());
                    redirect(redirectTo, request, response, "/profile");
                    break;
                } catch (EntityExistsException ex) {
                    request.setAttribute("emailError", ex.getMessage());
                } catch (ServiceException ex) {
                    request.setAttribute("error", ex.getMessage());
                }
                forward(SIGN_UP_JSP_PATH, request, response);
            }
            case "sign-in" -> {
                try {
                    if (!service.existsByEmail(emailParameter)) {
                        request.setAttribute("emailError", "Invalid email");
                        forward(SIGN_IN_JSP_PATH, request, response);
                        break;
                    }
                    if (!service.existsByEmailAndPassword(emailParameter, passwordParameter)) {
                        request.setAttribute("passwordError", "Invalid password");
                        forward(SIGN_IN_JSP_PATH, request, response);
                        break;
                    }
                    signInInSession(request, emailParameter, service.getRoleByEmail(emailParameter));
                    redirect(redirectTo, request, response, "/profile");
                    break;
                } catch (ServiceException ex) {
                    request.setAttribute("error", ex.getMessage());
                }
                forward(SIGN_IN_JSP_PATH, request, response);
            }
            case "sign-out" -> signOut(request, response);
            default -> forward(ERROR_JSP_PATH, request, response);
        }
    }

    private static void signInInSession(HttpServletRequest request, String email, Role role) {
        HttpSession session = request.getSession();
        session.setAttribute("email", email);
        session.setAttribute("role", role);
    }

    private static void forward(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }

    private static void redirect(
            String redirectTo, HttpServletRequest request, HttpServletResponse response, String defaultPath
    ) throws IOException {
        if (redirectTo != null && !redirectTo.isBlank()) {
            response.sendRedirect(redirectTo);
        } else {
            response.sendRedirect(request.getContextPath() + defaultPath);
        }
    }

    private static void signOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("email");
        session.removeAttribute("role");
        session.invalidate();

        String redirectTo = request.getParameter(REDIRECT_TO_PARAMETER);
        redirect(redirectTo, request, response, "/");
    }
}
