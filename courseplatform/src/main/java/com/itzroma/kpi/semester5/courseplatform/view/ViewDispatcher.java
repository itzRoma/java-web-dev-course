package com.itzroma.kpi.semester5.courseplatform.view;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

public class ViewDispatcher {
    private final View view;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public ViewDispatcher(View view, HttpServletRequest request, HttpServletResponse response) {
        this.view = view;
        this.request = new HttpServletRequestWrapper(request);
        this.response = new HttpServletResponseWrapper(response);
    }

    public void dispatch() throws ServletException, IOException {
        switch (view.getDispatchType()) {
            case FORWARD -> request.getRequestDispatcher(view.getJspPath()).forward(request, response);
            case REDIRECT -> response.sendRedirect(view.getUrl());
            default -> request.getRequestDispatcher(
                    View.internalServerError(request.getRequestURI(), DispatchType.FORWARD).getJspPath()
            ).forward(request, response);
        }
    }
}
