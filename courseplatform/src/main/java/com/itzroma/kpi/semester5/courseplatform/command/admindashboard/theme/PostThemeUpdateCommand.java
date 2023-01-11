package com.itzroma.kpi.semester5.courseplatform.command.admindashboard.theme;

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

public class PostThemeUpdateCommand extends Command {
    public PostThemeUpdateCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        String oldName = request.getParameter("oldName");
        String newName = request.getParameter("name");

        try {
            ThemeService service = new ThemeServiceImpl();
            service.update(service.findByName(oldName), new Theme(newName));
            return new View(JspPage.AD_THEMES, DispatchType.REDIRECT);
        } catch (EntityExistsException ex) {
            request.setAttribute("nameError", ex.getMessage());
        } catch (ServiceException ex) {
            request.setAttribute("error", ex.getMessage());
        }
        return new View(JspPage.AD_THEME_UPDATE, DispatchType.FORWARD);
    }
}
