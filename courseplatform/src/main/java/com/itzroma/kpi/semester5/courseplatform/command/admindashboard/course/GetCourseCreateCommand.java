package com.itzroma.kpi.semester5.courseplatform.command.admindashboard.course;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.service.impl.ThemeServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCourseCreateCommand extends Command {
    public GetCourseCreateCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        request.setAttribute("themes", new ThemeServiceImpl().findAll());
        return new View(JspPage.AD_COURSES_NEW, DispatchType.FORWARD);
    }
}
