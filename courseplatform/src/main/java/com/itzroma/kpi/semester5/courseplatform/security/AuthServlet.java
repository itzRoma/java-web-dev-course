package com.itzroma.kpi.semester5.courseplatform.security;

import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Role;
import com.itzroma.kpi.semester5.courseplatform.model.Student;
import com.itzroma.kpi.semester5.courseplatform.service.StudentService;
import com.itzroma.kpi.semester5.courseplatform.service.impl.StudentServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;
import com.itzroma.kpi.semester5.courseplatform.view.ViewDispatcher;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("redirectTo", request.getParameter(REDIRECT_TO_PARAMETER));

        View view = switch (extractAction(request)) {
            case "sign-up" -> new View(JspPage.SIGN_UP, DispatchType.FORWARD);
            case "sign-in" -> new View(JspPage.SIGN_IN, DispatchType.FORWARD);
            default -> View.notFound(request.getRequestURI(), DispatchType.FORWARD);
        };
        new ViewDispatcher(view, request, response).dispatch();
    }

    private static String extractAction(HttpServletRequest request) {
        String[] parts = request.getRequestURI().split("/");
        return parts[parts.length - 1];
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String emailParameter = request.getParameter("email");
        String passwordParameter = request.getParameter("password");

        String redirectTo = request.getParameter(REDIRECT_TO_PARAMETER);
        request.setAttribute("redirectTo", redirectTo);

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
                    signInInSession(request, student.getEmail(), student.getRole());
                    View view = View.fromUrl(redirectTo, JspPage.PROFILE, DispatchType.REDIRECT);
                    new ViewDispatcher(view, request, response).dispatch();
                    break;
                } catch (EntityExistsException ex) {
                    request.setAttribute("emailError", ex.getMessage());
                } catch (ServiceException ex) {
                    request.setAttribute("error", ex.getMessage());
                }
                View view = new View(JspPage.SIGN_UP, DispatchType.FORWARD);
                new ViewDispatcher(view, request, response).dispatch();
            }
            case "sign-in" -> {
                try {
                    if (!service.existsByEmail(emailParameter)) {
                        request.setAttribute("emailError", "Invalid email");
                        View view = new View(JspPage.SIGN_IN, DispatchType.FORWARD);
                        new ViewDispatcher(view, request, response).dispatch();
                        break;
                    }
                    if (!service.existsByEmailAndPassword(emailParameter, passwordParameter)) {
                        request.setAttribute("passwordError", "Invalid password");
                        View view = new View(JspPage.SIGN_IN, DispatchType.FORWARD);
                        new ViewDispatcher(view, request, response).dispatch();
                        break;
                    }
                    signInInSession(request, emailParameter, service.getRoleByEmail(emailParameter));
                    View view = View.fromUrl(redirectTo, JspPage.PROFILE, DispatchType.REDIRECT);
                    new ViewDispatcher(view, request, response).dispatch();
                    break;
                } catch (ServiceException ex) {
                    request.setAttribute("error", ex.getMessage());
                }
                View view = new View(JspPage.SIGN_IN, DispatchType.FORWARD);
                new ViewDispatcher(view, request, response).dispatch();
            }
            case "sign-out" -> signOut(request, response);
            default -> {
                View view = View.internalServerError(request.getRequestURI(), DispatchType.FORWARD);
                new ViewDispatcher(view, request, response).dispatch();
            }
        }
    }

    private static void signInInSession(HttpServletRequest request, String email, Role role) {
        HttpSession session = request.getSession();
        session.setAttribute("email", email);
        session.setAttribute("role", role);
    }

    private static void signOut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("email");
        session.removeAttribute("role");
        session.invalidate();

        View view = View.fromUrl(request.getParameter(REDIRECT_TO_PARAMETER), JspPage.INDEX, DispatchType.REDIRECT);
        new ViewDispatcher(view, request, response).dispatch();
    }
}
