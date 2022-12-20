package com.itzroma.kpi.semester5.courseplatform.command.profile;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Admin;
import com.itzroma.kpi.semester5.courseplatform.model.Student;
import com.itzroma.kpi.semester5.courseplatform.service.AdminService;
import com.itzroma.kpi.semester5.courseplatform.service.StudentService;
import com.itzroma.kpi.semester5.courseplatform.service.impl.AdminServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.service.impl.StudentServiceImpl;
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
        try {
            Long userId = Long.valueOf(request.getParameter("id"));
            switch (request.getSession().getAttribute("role").toString()) {
                case "STUDENT" -> {
                    StudentService service = new StudentServiceImpl();
                    Student student = service.updateByUserId(userId, new Student(
                            request.getParameter("first_name"),
                            request.getParameter("last_name"),
                            request.getParameter("email"),
                            null
                    ));
                    request.getSession().setAttribute("email", student.getEmail());
                    return new View(JspPage.PROFILE, DispatchType.REDIRECT);
                }
                case "ADMIN" -> {
                    AdminService service = new AdminServiceImpl();
                    Admin admin = service.updateByUserId(userId, new Admin(
                            request.getParameter("first_name"),
                            request.getParameter("last_name"),
                            request.getParameter("email"),
                            null
                    ));
                    request.getSession().setAttribute("email", admin.getEmail());
                    return new View(JspPage.PROFILE, DispatchType.REDIRECT);
                }
                default -> {
                    return View.internalServerError(request.getRequestURI(), DispatchType.FORWARD);
                }
            }
        } catch (EntityExistsException ex) {
            request.setAttribute("emailError", ex.getMessage());
        } catch (ServiceException ex) {
            request.setAttribute("error", ex.getMessage());
        }
        return new View(JspPage.UPDATE_PROFILE, DispatchType.FORWARD);
    }
}
