package com.itzroma.kpi.semester5.courseplatform.command.admindashboard;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.service.CourseService;
import com.itzroma.kpi.semester5.courseplatform.service.StudentService;
import com.itzroma.kpi.semester5.courseplatform.service.TeacherService;
import com.itzroma.kpi.semester5.courseplatform.service.impl.CourseServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.service.impl.StudentServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.service.impl.TeacherServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAdminDashboardCommand extends Command {
    private static final int DEFAULT_QUANTITY = 7;

    public GetAdminDashboardCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        try {
            StudentService studentService = new StudentServiceImpl();
            request.setAttribute("students", studentService.findMany(DEFAULT_QUANTITY));

            TeacherService teacherService = new TeacherServiceImpl();
            request.setAttribute("teachers", teacherService.findMany(DEFAULT_QUANTITY));

            CourseService courseService = new CourseServiceImpl();
            request.setAttribute("courses", courseService.findMany(DEFAULT_QUANTITY));
        } catch (ServiceException ex) {
            request.setAttribute("error", ex.getMessage());
        }
        return new View(JspPage.ADMIN_DASHBOARD, DispatchType.FORWARD);
    }
}
