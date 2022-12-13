package com.itzroma.kpi.semester5.courseplatform.security.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
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

        String redirectTo = req.getParameter("redirect-to");
        if (isLoggedIn(session)) {
            if (redirectTo != null && !redirectTo.isBlank()) {
                resp.sendRedirect(redirectTo);
            } else {
                resp.sendRedirect(req.getContextPath() + "/profile");
            }
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
