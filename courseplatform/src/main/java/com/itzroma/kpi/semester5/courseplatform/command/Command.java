package com.itzroma.kpi.semester5.courseplatform.command;

import com.itzroma.kpi.semester5.courseplatform.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Command {
    protected final HttpServletRequest request;
    protected final HttpServletResponse response;

    protected Command(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public abstract View execute();
}
