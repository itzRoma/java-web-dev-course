package com.itzroma.kpi.semester5.courseplatform.command.admindashboard.course;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Unit;
import com.itzroma.kpi.semester5.courseplatform.service.UnitService;
import com.itzroma.kpi.semester5.courseplatform.service.impl.CourseServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.service.impl.UnitServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostCourseUnitUpdateCommand extends Command {
    public PostCourseUnitUpdateCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        Long courseId = Long.parseLong(String.valueOf(request.getAttribute("id")));
        Long unitId = Long.parseLong(request.getParameter("unit_id"));
        String unitTitle = request.getParameter("unit_title");

        DispatchType dt;
        try {
            UnitService service = new UnitServiceImpl();
            service.update(service.findById(unitId), new Unit(unitTitle, courseId));
            dt = DispatchType.REDIRECT;
        } catch (EntityExistsException ex) {
            request.setAttribute("titleError", ex.getMessage());
            request.setAttribute("course", new CourseServiceImpl().findById(courseId));
            dt = DispatchType.FORWARD;
        } catch (ServiceException ex) {
            request.setAttribute("error", ex.getMessage());
            request.setAttribute("course", new CourseServiceImpl().findById(courseId));
            dt = DispatchType.FORWARD;
        }
        JspPage page = new JspPage(JspPage.AD_COURSE.jspPath(), JspPage.AD_COURSE.url().formatted(courseId));
        return new View(page, dt);
    }
}
