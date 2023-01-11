package com.itzroma.kpi.semester5.courseplatform.command.admindashboard;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.command.CommandFactory;
import com.itzroma.kpi.semester5.courseplatform.command.InternalServerErrorCommand;
import com.itzroma.kpi.semester5.courseplatform.command.admindashboard.course.ADCoursesCommandFactory;
import com.itzroma.kpi.semester5.courseplatform.command.admindashboard.student.ADStudentsCommandFactory;
import com.itzroma.kpi.semester5.courseplatform.command.admindashboard.teacher.ADTeachersCommandFactory;
import com.itzroma.kpi.semester5.courseplatform.command.admindashboard.theme.ADThemesCommandFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminDashboardCommandFactory extends CommandFactory {
    private static final String ADMIN_DASHBOARD_COMMAND_REGEX =
            "(?<=/admin-dashboard).*(?=\\?)|(?<=/admin-dashboard).*(?=$)";

    public AdminDashboardCommandFactory(HttpServletRequest request, HttpServletResponse response) {
        super(ADMIN_DASHBOARD_COMMAND_REGEX, request, response);
    }

    @Override
    public Command defineCommand() {
        if (action == null) return invalidCommand();

        if (action.equals("")) {
            return request.getMethod().equals("GET")
                    ? new GetAdminDashboardCommand(request, response)
                    : new InternalServerErrorCommand(request, response);
        }

        if (action.startsWith("/students")) {
            return new ADStudentsCommandFactory(request, response).defineCommand();
        }
        if (action.startsWith("/teachers")) {
            return new ADTeachersCommandFactory(request, response).defineCommand();
        }
        if (action.startsWith("/themes")) {
            return new ADThemesCommandFactory(request, response).defineCommand();
        }
        if (action.startsWith("/courses")) {
            return new ADCoursesCommandFactory(request, response).defineCommand();
        }

        return invalidCommand();
    }
}
