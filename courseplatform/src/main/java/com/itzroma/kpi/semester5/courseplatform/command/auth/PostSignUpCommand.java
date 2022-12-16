package com.itzroma.kpi.semester5.courseplatform.command.auth;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Role;
import com.itzroma.kpi.semester5.courseplatform.model.Student;
import com.itzroma.kpi.semester5.courseplatform.service.StudentService;
import com.itzroma.kpi.semester5.courseplatform.service.impl.StudentServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PostSignUpCommand extends Command {
    public PostSignUpCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        String firstNameParameter = request.getParameter("first_name");
        String lastNameParameter = request.getParameter("last_name");
        String emailParameter = request.getParameter("email");
        String passwordParameter = request.getParameter("password");

        String redirectTo = request.getParameter("redirect-to");
        request.setAttribute("redirectTo", redirectTo);

        // TODO: check role by email and create appropriate service
        StudentService service = new StudentServiceImpl();
        try {
            Student student = service.register(new Student(
                    firstNameParameter,
                    lastNameParameter,
                    emailParameter,
                    passwordParameter
            ));
            signInInSession(request, student.getEmail(), student.getRole());
            return View.fromUrl(redirectTo, JspPage.PROFILE, DispatchType.REDIRECT);
        } catch (EntityExistsException ex) {
            request.setAttribute("emailError", ex.getMessage());
        } catch (ServiceException ex) {
            request.setAttribute("error", ex.getMessage());
        }
        return new View(JspPage.SIGN_UP, DispatchType.FORWARD);
    }

    private static void signInInSession(HttpServletRequest request, String email, Role role) {
        HttpSession session = request.getSession();
        session.setAttribute("email", email);
        session.setAttribute("role", role);
    }
}