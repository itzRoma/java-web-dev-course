package com.itzroma.kpi.semester5.courseplatform.command.admindashboard;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.service.CourseService;
import com.itzroma.kpi.semester5.courseplatform.service.impl.CourseServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostCourseDeleteCommand extends Command {
    public PostCourseDeleteCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        CourseService service = new CourseServiceImpl();
        DispatchType dt;
        try {
            long id = Long.parseLong(String.valueOf(request.getAttribute("id")));
            service.delete(service.findById(id));
            dt = DispatchType.REDIRECT;
        } catch (ServiceException ex) {
            request.setAttribute("courses", service.findAll());
            request.setAttribute("error", ex.getMessage());
            dt = DispatchType.FORWARD;
        }
        return new View(JspPage.AD_COURSES, dt);
    }
}
