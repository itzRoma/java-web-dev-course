package com.itzroma.kpi.semester5.courseplatform.command.admindashboard.course;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.service.CourseService;
import com.itzroma.kpi.semester5.courseplatform.service.impl.CourseServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAllCoursesCommand extends Command {
    public GetAllCoursesCommand(HttpServletRequest request, HttpServletResponse response) {
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
