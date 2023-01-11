package com.itzroma.kpi.semester5.courseplatform.command.admindashboard;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.service.impl.CourseServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.service.impl.ThemeServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCourseUpdateCommand extends Command {
    public GetCourseUpdateCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        request.setAttribute("themes", new ThemeServiceImpl().findAll());
        long id = Long.parseLong(String.valueOf(request.getAttribute("id")));
        request.setAttribute("course", new CourseServiceImpl().findById(id));
        JspPage page = new JspPage(
                JspPage.AD_COURSE_UPDATE.jspPath(), JspPage.AD_COURSE_UPDATE.url().formatted(id)
        );
        return new View(page, DispatchType.FORWARD);
    }
}
