package com.itzroma.kpi.semester5.courseplatform.command.admindashboard;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Theme;
import com.itzroma.kpi.semester5.courseplatform.service.ThemeService;
import com.itzroma.kpi.semester5.courseplatform.service.impl.ThemeServiceImpl;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetThemeUpdatingCommand extends Command {
    public GetThemeUpdatingCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        ThemeService service = new ThemeServiceImpl();
        try {
            Theme theme = service.findByName(String.valueOf(request.getAttribute("name")));
            request.setAttribute("theme", theme);
            JspPage page = new JspPage(
                    JspPage.AD_THEME_UPDATE.jspPath(),
                    JspPage.AD_THEME_UPDATE.url().formatted(theme.getName())
            );
            return new View(page, DispatchType.FORWARD);
        } catch (EntityExistsException | ServiceException ex) {
            request.setAttribute("error", ex.getMessage());
            request.setAttribute("themes", service.findAll());
            return new View(JspPage.AD_THEMES, DispatchType.FORWARD);
        }
    }
}
