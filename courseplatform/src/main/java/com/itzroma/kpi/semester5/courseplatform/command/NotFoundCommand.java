package com.itzroma.kpi.semester5.courseplatform.command;

import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NotFoundCommand extends Command {
    public NotFoundCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        return View.notFound(request.getRequestURI(), DispatchType.FORWARD);
    }
}
