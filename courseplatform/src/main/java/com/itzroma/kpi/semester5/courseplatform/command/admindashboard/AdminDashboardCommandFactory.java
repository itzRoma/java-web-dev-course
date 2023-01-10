package com.itzroma.kpi.semester5.courseplatform.command.admindashboard;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.command.CommandFactory;
import com.itzroma.kpi.semester5.courseplatform.command.InternalServerErrorCommand;
import com.itzroma.kpi.semester5.courseplatform.command.NotFoundCommand;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminDashboardCommandFactory extends CommandFactory {
    private static final String ADMIN_DASHBOARD_COMMAND_REGEX = "(?<=/admin-dashboard).*(?=\\?)|(?<=/admin-dashboard).*(?=$)";

    private static final String STUDENT_VIEW_REGEX = "^/students/([\\w-.]+@\\w+(\\.\\w+)?)$";
    private static final String STUDENT_TOGGLE_BLOCK_REGEX = "^/students/([\\w-.]+@\\w+(\\.\\w+)?)/toggle-block$";

    private static final String TEACHER_VIEW_REGEX = "^/teachers/([\\w-.]+@\\w+(\\.\\w+)?)$";

    public AdminDashboardCommandFactory(HttpServletRequest request, HttpServletResponse response) {
        super(ADMIN_DASHBOARD_COMMAND_REGEX, request, response);
    }

    @Override
    public Command defineCommand() {
        if (action == null) return invalidCommand();

        return switch (action) {
            case "" -> request.getMethod().equals("GET")
                    ? new GetAdminDashboardCommand(request, response)
                    : new InternalServerErrorCommand(request, response);
            case "/students" -> request.getMethod().equals("GET")
                    ? new GetStudentsCommand(request, response)
                    : new InternalServerErrorCommand(request, response);
            case "/teachers" -> request.getMethod().equals("GET")
                    ? new GetTeachersCommand(request, response)
                    : new InternalServerErrorCommand(request, response);
            case "/teachers/new" -> request.getMethod().equals("GET")
                    ? new GetTeacherCreationCommand(request, response)
                    : new PostTeacherCreationCommand(request, response);
            case "/courses" -> request.getMethod().equals("GET")
                    ? new GetCoursesCommand(request, response)
                    : new InternalServerErrorCommand(request, response);
            case "/courses/new" -> request.getMethod().equals("GET")
                    ? new GetCourseCreationCommand(request, response)
                    : new PostCourseCreationCommand(request, response);
            default -> {
                if (action.matches(STUDENT_VIEW_REGEX)) {
                    yield viewStudent();
                }
                if (action.matches(STUDENT_TOGGLE_BLOCK_REGEX)) {
                    yield toggleBlockForStudent();
                }
                if (action.matches(TEACHER_VIEW_REGEX)) {
                    yield viewTeacher();
                }
                yield invalidCommand();
            }
        };
    }

    private Command viewStudent() {
        extractEmail(STUDENT_VIEW_REGEX, "Student not found");
        return request.getMethod().equals("GET")
                ? new GetStudentCommand(request, response)
                : new InternalServerErrorCommand(request, response);
    }

    private void extractEmail(String regex, String notFoundMessage) {
        Matcher matcher = Pattern.compile(regex).matcher(Objects.requireNonNull(action));
        if (matcher.find()) {
            request.setAttribute("email", matcher.group(1));
        } else {
            throw new EntityNotFoundException(notFoundMessage);
        }
    }

    private Command toggleBlockForStudent() {
        extractEmail(STUDENT_TOGGLE_BLOCK_REGEX, "Student not found");
        return request.getMethod().equals("GET")
                ? new NotFoundCommand(request, response)
                : new PostToggleBlockCommand(request, response);
    }

    private Command viewTeacher() {
        extractEmail(TEACHER_VIEW_REGEX, "Teacher not found");
        return request.getMethod().equals("GET")
                ? new GetTeacherCommand(request, response)
                : new InternalServerErrorCommand(request, response);
    }
}
