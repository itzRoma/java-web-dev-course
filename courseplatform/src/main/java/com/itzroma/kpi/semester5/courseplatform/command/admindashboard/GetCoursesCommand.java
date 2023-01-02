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

public class GetCoursesCommand extends Command {
    public GetCoursesCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        try {
            CourseService service = new CourseServiceImpl();
            request.setAttribute("courses", service.findAll());
        } catch (ServiceException ex) {
            request.setAttribute("error", ex.getMessage());
        }
        return new View(JspPage.AD_COURSES, DispatchType.FORWARD);
    }
}
