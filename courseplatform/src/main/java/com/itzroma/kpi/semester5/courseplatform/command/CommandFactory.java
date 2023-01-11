package com.itzroma.kpi.semester5.courseplatform.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class CommandFactory {
    protected final HttpServletRequest request;
    protected final HttpServletResponse response;
    protected final String action;

    protected CommandFactory(String actionExtractionRegex, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.action = extractAction(actionExtractionRegex);
    }

    private String extractAction(String actionExtractionRegex) {
        Matcher matcher = Pattern.compile(request.getContextPath().concat(actionExtractionRegex))
                .matcher(request.getRequestURI());
        if (matcher.find()) {
            return matcher.group().endsWith("/")
                    ? matcher.group().substring(0, matcher.group().length() - 1)
                    : matcher.group();
        } else {
            return null;
        }
    }

    public abstract Command defineCommand();

    protected Command invalidCommand() {
        return request.getMethod().equals("GET")
                ? new NotFoundCommand(request, response)
                : new InternalServerErrorCommand(request, response);
    }

    protected Command instantiateCommand(Class<? extends Command> commandClass) {
        try {
            return commandClass
                    .getConstructor(HttpServletRequest.class, HttpServletResponse.class)
                    .newInstance(request, response);
        } catch (ReflectiveOperationException ex) {
            return new InternalServerErrorCommand(request, response);
        }
    }
}
