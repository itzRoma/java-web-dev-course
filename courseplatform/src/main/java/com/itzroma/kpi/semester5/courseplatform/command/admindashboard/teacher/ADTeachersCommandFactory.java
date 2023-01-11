package com.itzroma.kpi.semester5.courseplatform.command.admindashboard.teacher;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.command.CommandFactory;
import com.itzroma.kpi.semester5.courseplatform.command.InternalServerErrorCommand;
import com.itzroma.kpi.semester5.courseplatform.command.NotFoundCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ADTeachersCommandFactory extends CommandFactory {
    private static final String AD_TEACHERS_COMMAND_REGEX =
            "(?<=/admin-dashboard/teachers).*(?=\\?)|(?<=/admin-dashboard/teachers).*(?=$)";

    private static final String TEACHER_EMAIL_EXTRACTION_REGEX = "/([\\w-.]+@\\w+(\\.\\w+)?)";

    private static final String TEACHER_VIEW_REGEX = "^/([\\w-.]+@\\w+(\\.\\w+)?)$";

    public ADTeachersCommandFactory(HttpServletRequest request, HttpServletResponse response) {
        super(AD_TEACHERS_COMMAND_REGEX, request, response);
    }

    @Override
    public Command defineCommand() {
        if (action == null) return invalidCommand();

        if (action.equals("")) {
            return defineCommand(false, GetAllTeachersCommand.class, InternalServerErrorCommand.class);
        }
        if (action.equals("/new")) {
            return defineCommand(false, GetTeacherCreateCommand.class, PostTeacherCreateCommand.class);
        }
        if (action.matches(TEACHER_VIEW_REGEX)) {
            return defineCommand(true, GetOneTeacherCommand.class, InternalServerErrorCommand.class);
        }

        return invalidCommand();
    }

    private Command defineCommand(boolean extractEmail, Class<? extends Command> get, Class<? extends Command> post) {
        if (extractEmail) {
            Matcher matcher = Pattern.compile(TEACHER_EMAIL_EXTRACTION_REGEX)
                    .matcher(Objects.requireNonNull(action));
            if (matcher.find()) {
                request.setAttribute("email", matcher.group(1));
            } else {
                return new NotFoundCommand(request, response);
            }
        }
        return request.getMethod().equals("GET")
                ? instantiateCommand(get)
                : instantiateCommand(post);
    }
}