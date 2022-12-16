package com.itzroma.kpi.semester5.courseplatform.command.auth;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Role;
import com.itzroma.kpi.semester5.courseplatform.service.StudentService;
import com.itzroma.kpi.semester5.courseplatform.service.impl.StudentServiceImpl;
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
        String emailParameter = request.getParameter("email");
        String passwordParameter = request.getParameter("password");

        String redirectTo = request.getParameter("redirect-to");
        request.setAttribute("redirectTo", redirectTo);

        // TODO: replace this concrete implementation
        StudentService service = new StudentServiceImpl();
        try {
            if (!service.existsByEmail(emailParameter)) {
                request.setAttribute("emailError", "Invalid email");
                return new View(JspPage.SIGN_IN, DispatchType.FORWARD);
            }
            if (!service.existsByEmailAndPassword(emailParameter, passwordParameter)) {
                request.setAttribute("passwordError", "Invalid password");
                return new View(JspPage.SIGN_IN, DispatchType.FORWARD);
            }
            signInInSession(request, emailParameter, service.getRoleByEmail(emailParameter));
            return View.fromUrl(redirectTo, JspPage.PROFILE, DispatchType.REDIRECT);
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
