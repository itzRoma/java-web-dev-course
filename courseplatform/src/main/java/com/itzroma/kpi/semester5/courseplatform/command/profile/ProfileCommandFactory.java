package com.itzroma.kpi.semester5.courseplatform.command.profile;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.command.CommandFactory;
import com.itzroma.kpi.semester5.courseplatform.command.InternalServerErrorCommand;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Role;
import com.itzroma.kpi.semester5.courseplatform.service.impl.AdminServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.service.impl.StudentServiceImpl;

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

        try {
            if (request.getSession().getAttribute("role") == Role.ADMIN) {
                request.setAttribute(
                        "user",
                        new AdminServiceImpl().findByEmail((String) request.getSession().getAttribute("email"))
                );
            } else {
                request.setAttribute(
                        "user",
                        new StudentServiceImpl().findByEmail((String) request.getSession().getAttribute("email"))
                );
            }
        } catch (ServiceException ex) {
            return new InternalServerErrorCommand(request, response);
        }

        return switch (action.endsWith("/") ? action.substring(0, action.length() - 1) : action) {
            case "" -> request.getMethod().equals("GET")
                    ? new GetProfileCommand(request, response)
                    : new InternalServerErrorCommand(request, response);
            default -> invalidCommand();
        };
    }
}