package com.itzroma.kpi.semester5.courseplatform.command.auth;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetSignUpCommand extends Command {
    public GetSignUpCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        request.setAttribute("redirectTo", request.getParameter("redirect-to"));
        request.setAttribute("isAdmin", request.getParameter("is-admin"));
        return new View(JspPage.SIGN_UP, DispatchType.FORWARD);
    }
}
