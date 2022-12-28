package com.itzroma.kpi.semester5.courseplatform.command.profile;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.*;
import com.itzroma.kpi.semester5.courseplatform.service.AdminService;
import com.itzroma.kpi.semester5.courseplatform.service.StudentService;
import com.itzroma.kpi.semester5.courseplatform.service.TeacherService;
import com.itzroma.kpi.semester5.courseplatform.service.impl.AdminServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.service.impl.StudentServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.service.impl.TeacherServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostUpdateProfileCommand extends Command {
    public PostUpdateProfileCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        String firstNameParameter = request.getParameter("first_name");
        String lastNameParameter = request.getParameter("last_name");
        String emailParameter = request.getParameter("email");

        try {
            Long targetId = Long.valueOf(request.getParameter("id"));
            User updated;
            switch (Role.valueOf(request.getSession().getAttribute("role").toString())) {
                case STUDENT -> {
                    StudentService service = new StudentServiceImpl();
                    updated = service.updateByUserId(targetId, new Student(
                            firstNameParameter, lastNameParameter, emailParameter, null
                    ));
                }
                case TEACHER -> {
                    TeacherService service = new TeacherServiceImpl();
                    updated = service.updateByUserId(targetId, new Teacher(
                            firstNameParameter, lastNameParameter, emailParameter, null
                    ));
                }
                case ADMIN -> {
                    AdminService service = new AdminServiceImpl();
                    updated = service.updateByUserId(targetId, new Admin(
                            firstNameParameter, lastNameParameter, emailParameter, null
                    ));
                }
                default -> {
                    return View.internalServerError(request.getRequestURI(), DispatchType.FORWARD);
                }
            }
            request.getSession().setAttribute("email", updated.getEmail());
            return new View(JspPage.PROFILE, DispatchType.REDIRECT);
        } catch (EntityExistsException ex) {
            request.setAttribute("emailError", ex.getMessage());
        } catch (ServiceException ex) {
            request.setAttribute("error", ex.getMessage());
        }
        return new View(JspPage.UPDATE_PROFILE, DispatchType.FORWARD);
    }
}
