package com.itzroma.kpi.semester5.courseplatform.security.filter;

import com.itzroma.kpi.semester5.courseplatform.view.DispatchType;
import com.itzroma.kpi.semester5.courseplatform.view.JspPage;
import com.itzroma.kpi.semester5.courseplatform.view.View;
import com.itzroma.kpi.semester5.courseplatform.view.ViewDispatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthFilter", value = {"/auth/sign-in", "/auth/sign-up"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        View view = View.fromUrl(req.getParameter("redirect-to"), JspPage.INDEX, DispatchType.REDIRECT);
        if (isLoggedIn(session)) {
            new ViewDispatcher(view, req, resp).dispatch();
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isLoggedIn(HttpSession session) {
        return session != null
                && session.getAttribute("email") != null
                && session.getAttribute("role") != null;
    }
}
