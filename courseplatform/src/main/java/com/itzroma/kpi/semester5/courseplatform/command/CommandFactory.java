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
        Pattern pattern = Pattern.compile(request.getContextPath().concat(actionExtractionRegex));
        Matcher matcher = pattern.matcher(request.getRequestURI());
        return matcher.find() ? matcher.group() : null;
    }

    public abstract Command defineCommand();

    protected Command invalidCommand() {
        return request.getMethod().equals("GET")
                ? new ViewNotFoundCommand(request, response)
                : new ViewInternalServerErrorCommand(request, response);
    }
}
