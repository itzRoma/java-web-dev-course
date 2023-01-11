package com.itzroma.kpi.semester5.courseplatform.command.admindashboard.teacher;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Teacher;
import com.itzroma.kpi.semester5.courseplatform.service.impl.TeacherServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostTeacherCreateCommand extends Command {
    public PostTeacherCreateCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        String firstNameParameter = request.getParameter("first_name");
        String lastNameParameter = request.getParameter("last_name");
        String emailParameter = request.getParameter("email");
        String passwordParameter = request.getParameter("password");

        try {
            new TeacherServiceImpl().register(new Teacher(
                    firstNameParameter, lastNameParameter, emailParameter, passwordParameter
            ));
            return new View(JspPage.AD_TEACHERS, DispatchType.REDIRECT);
        } catch (EntityExistsException ex) {
            request.setAttribute("emailError", ex.getMessage());
        } catch (ServiceException ex) {
            request.setAttribute("error", ex.getMessage());
        }
        return new View(JspPage.AD_TEACHERS, DispatchType.REDIRECT);
    }
}
