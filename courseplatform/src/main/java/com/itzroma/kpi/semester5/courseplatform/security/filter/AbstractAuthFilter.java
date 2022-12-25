package com.itzroma.kpi.semester5.courseplatform.security.filter;

import javax.servlet.Filter;
import javax.servlet.http.HttpSession;

public abstract class AbstractAuthFilter implements Filter {
    protected boolean isLoggedIn(HttpSession session) {
        return session != null
                && session.getAttribute("email") != null
                && session.getAttribute("role") != null;
    }
}
