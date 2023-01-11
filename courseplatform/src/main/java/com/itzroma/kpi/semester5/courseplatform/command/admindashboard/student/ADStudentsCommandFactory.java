package com.itzroma.kpi.semester5.courseplatform.command.admindashboard.student;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.command.CommandFactory;
import com.itzroma.kpi.semester5.courseplatform.command.InternalServerErrorCommand;
import com.itzroma.kpi.semester5.courseplatform.command.NotFoundCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ADStudentsCommandFactory extends CommandFactory {
    private static final String AD_STUDENTS_COMMAND_REGEX =
            "(?<=/admin-dashboard/students).*(?=\\?)|(?<=/admin-dashboard/students).*(?=$)";

    private static final String STUDENT_EMAIL_EXTRACTION_REGEX = "/([\\w-.]+@\\w+(\\.\\w+)?)";

    private static final String STUDENT_VIEW_REGEX = "^/([\\w-.]+@\\w+(\\.\\w+)?)$";
    private static final String STUDENT_TOGGLE_BLOCK_REGEX = "^/([\\w-.]+@\\w+(\\.\\w+)?)/toggle-block$";

    public ADStudentsCommandFactory(HttpServletRequest request, HttpServletResponse response) {
        super(AD_STUDENTS_COMMAND_REGEX, request, response);
    }

    @Override
    public Command defineCommand() {
        if (action == null) return invalidCommand();

        if (action.equals("")) {
            return defineCommand(false, GetAllStudentsCommand.class, InternalServerErrorCommand.class);
        }
        if (action.matches(STUDENT_VIEW_REGEX)) {
            return defineCommand(true, GetOneStudentCommand.class, InternalServerErrorCommand.class);
        }
        if (action.matches(STUDENT_TOGGLE_BLOCK_REGEX)) {
            return defineCommand(true, NotFoundCommand.class, PostToggleBlockForStudentCommand.class);
        }

        return invalidCommand();
    }

    private Command defineCommand(boolean extractEmail, Class<? extends Command> get, Class<? extends Command> post) {
        if (extractEmail) {
            Matcher matcher = Pattern.compile(STUDENT_EMAIL_EXTRACTION_REGEX)
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
