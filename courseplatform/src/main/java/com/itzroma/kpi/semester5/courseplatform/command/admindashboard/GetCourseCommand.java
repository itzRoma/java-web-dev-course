package com.itzroma.kpi.semester5.courseplatform.command.admindashboard;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityNotFoundException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Course;
import com.itzroma.kpi.semester5.courseplatform.service.CourseService;
import com.itzroma.kpi.semester5.courseplatform.service.impl.CourseServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCourseCommand extends Command {
    public GetCourseCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        try {
            CourseService service = new CourseServiceImpl();
            long id = Long.parseLong(String.valueOf(request.getAttribute("id")));
            Course course = service.findById(id);
            request.setAttribute("course", course);

            JspPage page = new JspPage(JspPage.AD_COURSE.jspPath(), JspPage.AD_COURSE.url().formatted(id));
            return new View(page, DispatchType.FORWARD);
        } catch (EntityNotFoundException ex) {
            request.setAttribute("error", ex.getMessage());
            return View.notFound(request.getRequestURI(), DispatchType.FORWARD);
        } catch (ServiceException ex) {
            request.setAttribute("error", ex.getMessage());
            return new View(JspPage.AD_COURSES, DispatchType.FORWARD);
        }
    }
}
