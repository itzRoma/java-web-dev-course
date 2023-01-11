package com.itzroma.kpi.semester5.courseplatform.command.admindashboard.course;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Course;
import com.itzroma.kpi.semester5.courseplatform.model.Theme;
import com.itzroma.kpi.semester5.courseplatform.service.ThemeService;
import com.itzroma.kpi.semester5.courseplatform.service.impl.CourseServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.service.impl.ThemeServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class PostCourseCreateCommand extends Command {
    public PostCourseCreateCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String[] themes = request.getParameterValues("themes");
        int duration = Integer.parseInt(request.getParameter("duration"));
        LocalDateTime startingDate = LocalDateTime.parse(request.getParameter("starting_date"));

        if (startingDate.isBefore(LocalDateTime.now())) {
            request.setAttribute("dateError", "Starting date is in the past");
            return new View(JspPage.AD_COURSES_NEW, DispatchType.FORWARD);
        }

        try {
            ThemeService themeService = new ThemeServiceImpl();
            List<Theme> themesList = Arrays.stream(themes == null ? new String[0] : themes)
                    .map(themeService::findByName)
                    .toList();
            new CourseServiceImpl().create(new Course(title, description, duration, startingDate, themesList));
        } catch (ServiceException ex) {
            request.setAttribute("error", ex.getMessage());
        }
        return new View(JspPage.AD_COURSES, DispatchType.REDIRECT);
    }
}
