package com.itzroma.kpi.semester5.courseplatform.command.admindashboard.theme;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.service.ThemeService;
import com.itzroma.kpi.semester5.courseplatform.service.impl.ThemeServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostThemeDeleteCommand extends Command {
    public PostThemeDeleteCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        ThemeService service = new ThemeServiceImpl();
        DispatchType dt;
        try {
            service.delete(service.findByName(String.valueOf(request.getAttribute("name"))));
            dt = DispatchType.REDIRECT;
        } catch (ServiceException ex) {
            request.setAttribute("error", ex.getMessage());
            request.setAttribute("themes", service.findAll());
            dt = DispatchType.FORWARD;
        }
        return new View(JspPage.AD_THEMES, dt);
    }
}
