package com.itzroma.kpi.semester5.courseplatform.command.admindashboard.theme;

import com.itzroma.kpi.semester5.courseplatform.command.Command;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetThemeCreateCommand extends Command {
    public GetThemeCreateCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public View execute() {
        return new View(JspPage.AD_THEMES_NEW, DispatchType.FORWARD);
    }
}
