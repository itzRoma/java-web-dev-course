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

@WebFilter(filterName = "AuthenticationFilter", value = "/profile/*")
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        if (isLoggedIn(session)) {
            chain.doFilter(request, response);
        } else {
            View view = new View(JspPage.SIGN_IN, DispatchType.REDIRECT);
            view.addQueryParameter("redirect-to", req.getRequestURI());
            new ViewDispatcher(view, req, resp).dispatch();
        }
    }

    private boolean isLoggedIn(HttpSession session) {
        return session != null
                && session.getAttribute("email") != null
                && session.getAttribute("role") != null;
    }
}
