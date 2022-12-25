package com.itzroma.kpi.semester5.courseplatform.security.filter;

import com.itzroma.kpi.semester5.courseplatform.model.Role;
import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.View;
import com.itzroma.kpi.semester5.courseplatform.view.ViewDispatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AdminAuthorizationFilter", value = "/admin-dashboard/*")
public class AdminAuthorizationFilter extends AbstractAuthFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        if (isLoggedIn(session) && session.getAttribute("role") == Role.ADMIN) {
            chain.doFilter(request, response);
        } else {
            new ViewDispatcher(View.notFound(req.getRequestURI(), DispatchType.FORWARD), req, resp).dispatch();
        }
    }
}
