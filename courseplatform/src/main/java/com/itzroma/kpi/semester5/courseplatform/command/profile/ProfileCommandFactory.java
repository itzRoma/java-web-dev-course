package com.itzroma.kpi.semester5.courseplatform.command.profile;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.command.CommandFactory;
import com.itzroma.kpi.semester5.courseplatform.command.InternalServerErrorCommand;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Role;
import com.itzroma.kpi.semester5.courseplatform.service.impl.AdminServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.service.impl.StudentServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.service.impl.TeacherServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileCommandFactory extends CommandFactory {
    private static final String PROFILE_COMMAND_REGEX = "(?<=/profile).*(?=\\?)|(?<=/profile).*(?=$)";

    public ProfileCommandFactory(HttpServletRequest request, HttpServletResponse response) {
        super(PROFILE_COMMAND_REGEX, request, response);
    }

    @Override
    public Command defineCommand() {
        if (action == null) return invalidCommand();

        String emailAttribute = (String) request.getSession().getAttribute("email");
        try {
            switch (Role.valueOf(request.getSession().getAttribute("role").toString())) {
                case STUDENT -> request.setAttribute(
                        "user", new StudentServiceImpl().findByEmail(emailAttribute)
                );
                case TEACHER -> request.setAttribute(
                        "user", new TeacherServiceImpl().findByEmail(emailAttribute)
                );
                case ADMIN -> request.setAttribute(
                        "user", new AdminServiceImpl().findByEmail(emailAttribute)
                );
                default -> new InternalServerErrorCommand(request, response);
            }
        } catch (ServiceException ex) {
            return new InternalServerErrorCommand(request, response);
        }

        return switch (action) {
            case "" -> request.getMethod().equals("GET")
                    ? new GetProfileCommand(request, response)
                    : new InternalServerErrorCommand(request, response);
            case "/update" -> request.getMethod().equals("GET")
                    ? new GetUpdateProfileCommand(request, response)
                    : new PostUpdateProfileCommand(request, response);
            default -> invalidCommand();
        };
    }
}
