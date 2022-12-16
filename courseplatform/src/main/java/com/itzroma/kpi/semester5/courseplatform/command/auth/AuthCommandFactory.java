package com.itzroma.kpi.semester5.courseplatform.command.auth;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.command.CommandFactory;
import com.itzroma.kpi.semester5.courseplatform.command.ViewNotFoundCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthCommandFactory extends CommandFactory {
    private static final String AUTH_COMMAND_PATTERN = "(?<=/auth/).*(?=\\?)|(?<=/auth/).*(?=$)";

    public AuthCommandFactory(HttpServletRequest request, HttpServletResponse response) {
        super(AUTH_COMMAND_PATTERN, request, response);
    }

    @Override
    public Command defineCommand() {
        if (action == null) return invalidCommand();

        return switch (action) {
            case "sign-in" -> request.getMethod().equals("GET")
                    ? new GetSignInCommand(request, response)
                    : new PostSignInCommand(request, response);
            case "sign-up" -> request.getMethod().equals("GET")
                    ? new GetSignUpCommand(request, response)
                    : new PostSignUpCommand(request, response);
            case "sign-out" -> request.getMethod().equals("GET")
                    ? new ViewNotFoundCommand(request, response)
                    : new PostSignOutCommand(request, response);
            default -> invalidCommand();
        };
    }
}
