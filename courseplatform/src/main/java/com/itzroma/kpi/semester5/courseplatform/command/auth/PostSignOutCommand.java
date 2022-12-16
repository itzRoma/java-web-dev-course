package com.itzroma.kpi.semester5.courseplatform.command.auth;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PostSignOutCommand extends Command {
    public PostSignOutCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        HttpSession session = request.getSession();
        session.removeAttribute("email");
        session.removeAttribute("role");
        session.invalidate();

        return View.fromUrl(request.getParameter("redirect-to"), JspPage.INDEX, DispatchType.REDIRECT);
    }
}
