package com.itzroma.kpi.semester5.courseplatform.command.auth;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Role;
import com.itzroma.kpi.semester5.courseplatform.model.User;
import com.itzroma.kpi.semester5.courseplatform.service.StudentService;
import com.itzroma.kpi.semester5.courseplatform.service.UserService;
import com.itzroma.kpi.semester5.courseplatform.service.impl.AdminServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.service.impl.StudentServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.service.impl.TeacherServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PostSignInCommand extends Command {
    public PostSignInCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        Role roleParameter = Role.valueOf(request.getParameter("role"));

        String emailParameter = request.getParameter("email");
        String passwordParameter = request.getParameter("password");

        String redirectToParameter = request.getParameter("redirect-to");
        request.setAttribute("redirectTo", redirectToParameter);

        UserService<Long, ? extends User> service;
        switch (roleParameter) {
            case STUDENT -> service = new StudentServiceImpl();
            case TEACHER -> service = new TeacherServiceImpl();
            default -> {
                return View.internalServerError(request.getRequestURI(), DispatchType.FORWARD);
            }
        }

        boolean isAdmin = Boolean.parseBoolean(request.getParameter("is-admin"));
        if (isAdmin) {
            service = new AdminServiceImpl();
        }

        try {
            if (!service.existsByEmail(emailParameter)) {
                request.setAttribute("emailError", "Invalid email");
                return new View(JspPage.SIGN_IN, DispatchType.FORWARD);
            }
            if (!service.existsByEmailAndPassword(emailParameter, passwordParameter)) {
                request.setAttribute("passwordError", "Invalid password");
                return new View(JspPage.SIGN_IN, DispatchType.FORWARD);
            }
            if (!isAdmin && roleParameter == Role.STUDENT) {
                StudentService studentService = (StudentService) service;
                if (studentService.isStudentBlocked(emailParameter)) {
                    request.setAttribute("error", "Your account is blocked");
                    return new View(JspPage.SIGN_IN, DispatchType.FORWARD);
                }
            }
            signInInSession(request, emailParameter, isAdmin ? Role.ADMIN : roleParameter);
            return View.fromUrl(redirectToParameter, JspPage.PROFILE, DispatchType.REDIRECT);
        } catch (ServiceException ex) {
            request.setAttribute("error", ex.getMessage());
        }
        return new View(JspPage.SIGN_IN, DispatchType.FORWARD);
    }

    private static void signInInSession(HttpServletRequest request, String email, Role role) {
        HttpSession session = request.getSession();
        session.setAttribute("email", email);
        session.setAttribute("role", role);
    }
}
