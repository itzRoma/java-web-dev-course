package com.itzroma.kpi.semester5.courseplatform.command.admindashboard.teacher;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Teacher;
import com.itzroma.kpi.semester5.courseplatform.service.TeacherService;
import com.itzroma.kpi.semester5.courseplatform.service.impl.TeacherServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetOneTeacherCommand extends Command {
    public GetOneTeacherCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        try {
            TeacherService service = new TeacherServiceImpl();
            Teacher teacher = service.findByEmail(String.valueOf(request.getAttribute("email")));
            request.setAttribute("user", teacher);
            return new View(JspPage.PROFILE, DispatchType.FORWARD);
        } catch (ServiceException ex) {
            request.setAttribute("error", ex.getMessage());
            return View.notFound(request.getRequestURI(), DispatchType.FORWARD);
        }
    }
}
