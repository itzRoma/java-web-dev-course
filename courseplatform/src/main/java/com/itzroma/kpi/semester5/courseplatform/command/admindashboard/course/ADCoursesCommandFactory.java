package com.itzroma.kpi.semester5.courseplatform.command.admindashboard.course;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.command.CommandFactory;
import com.itzroma.kpi.semester5.courseplatform.command.InternalServerErrorCommand;
import com.itzroma.kpi.semester5.courseplatform.command.NotFoundCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ADCoursesCommandFactory extends CommandFactory {
    private static final String AD_COURSES_COMMAND_REGEX =
            "(?<=/admin-dashboard/courses).*(?=\\?)|(?<=/admin-dashboard/courses).*(?=$)";

    private static final String COURSE_ID_EXTRACTION_REGEX = "/(\\d+)";

    private static final String COURSE_VIEW_REGEX = "^/(\\d+)$";
    private static final String COURSE_UPDATE_REGEX = "^/(\\d+)/update$";
    private static final String COURSE_DELETE_REGEX = "^/(\\d+)/delete$";

    public ADCoursesCommandFactory(HttpServletRequest request, HttpServletResponse response) {
        super(AD_COURSES_COMMAND_REGEX, request, response);
    }

    @Override
    public Command defineCommand() {
        if (action == null) return invalidCommand();

        if (action.equals("")) {
            return defineCommand(false, GetAllCoursesCommand.class, InternalServerErrorCommand.class);
        }
        if (action.equals("/new")) {
            return defineCommand(false, GetCourseCreateCommand.class, PostCourseCreateCommand.class);
        }
        if (action.matches(COURSE_VIEW_REGEX)) {
            return defineCommand(true, GetOneCourseCommand.class, InternalServerErrorCommand.class);
        }
        if (action.matches(COURSE_UPDATE_REGEX)) {
            return defineCommand(true, GetCourseUpdateCommand.class, PostCourseUpdateCommand.class);
        }
        if (action.matches(COURSE_DELETE_REGEX)) {
            return defineCommand(true, NotFoundCommand.class, PostCourseDeleteCommand.class);
        }

        return invalidCommand();
    }

    private Command defineCommand(boolean extractId, Class<? extends Command> get, Class<? extends Command> post) {
        if (extractId) {
            Matcher matcher = Pattern.compile(COURSE_ID_EXTRACTION_REGEX).matcher(Objects.requireNonNull(action));
            if (matcher.find()) {
                request.setAttribute("id", Long.parseLong(matcher.group(1)));
            } else {
                return new NotFoundCommand(request, response);
            }
        }
        return request.getMethod().equals("GET")
                ? instantiateCommand(get)
                : instantiateCommand(post);
    }
}
