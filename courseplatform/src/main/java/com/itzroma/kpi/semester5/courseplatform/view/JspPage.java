package com.itzroma.kpi.semester5.courseplatform.view;

import java.util.Set;

public record JspPage(String jspPath, String url) {
    private static final String CONTEXT_PATH = "/courseplatform";
    private static final String VIEW_DIR = "/WEB-INF/view";

    private static final Set<JspPage> DEFAULTS;

    public static final JspPage INDEX = new JspPage(
            "/index.jsp", CONTEXT_PATH
    );

    public static final JspPage SIGN_IN = new JspPage(
            "/sign-in.jsp", CONTEXT_PATH.concat("/auth/sign-in")
    );
    public static final JspPage SIGN_UP = new JspPage(
            "/sign-up.jsp", CONTEXT_PATH.concat("/auth/sign-up")
    );

    public static final JspPage PROFILE = new JspPage(
            VIEW_DIR.concat("/profile/index.jsp"), CONTEXT_PATH.concat("/profile")
    );
    public static final JspPage UPDATE_PROFILE = new JspPage(
            VIEW_DIR.concat("/profile/update.jsp"), CONTEXT_PATH.concat("/profile/update")
    );

    public static final JspPage ADMIN_DASHBOARD = new JspPage(
            VIEW_DIR.concat("/admin/dashboard.jsp"), CONTEXT_PATH.concat("/admin-dashboard")
    );
    public static final JspPage AD_STUDENTS = new JspPage(
            VIEW_DIR.concat("/admin/students.jsp"), CONTEXT_PATH.concat("/admin-dashboard/students")
    );
    public static final JspPage AD_TEACHERS = new JspPage(
            VIEW_DIR.concat("/admin/teacher/all.jsp"), CONTEXT_PATH.concat("/admin-dashboard/teachers")
    );
    public static final JspPage AD_TEACHERS_NEW = new JspPage(
            VIEW_DIR.concat("/admin/teacher/new.jsp"), CONTEXT_PATH.concat("/admin-dashboard/teachers/new")
    );
    public static final JspPage AD_COURSES = new JspPage(
            VIEW_DIR.concat("/admin/course/all.jsp"), CONTEXT_PATH.concat("/admin-dashboard/courses")
    );
    public static final JspPage AD_COURSES_NEW = new JspPage(
            VIEW_DIR.concat("/admin/course/new.jsp"), CONTEXT_PATH.concat("/admin-dashboard/courses/new")
    );
    public static final JspPage AD_COURSE = new JspPage(
            VIEW_DIR.concat("/admin/course/one.jsp"), CONTEXT_PATH.concat("/admin-dashboard/courses/%d")
    );
    public static final JspPage AD_COURSE_UPDATE = new JspPage(
            VIEW_DIR.concat("/admin/course/update.jsp"), CONTEXT_PATH.concat("/admin-dashboard/courses/%d/update")
    );

    public static final JspPage AD_THEMES = new JspPage(
            VIEW_DIR.concat("/admin/theme/all.jsp"), CONTEXT_PATH.concat("/admin-dashboard/themes")
    );
    public static final JspPage AD_THEMES_NEW = new JspPage(
            VIEW_DIR.concat("/admin/theme/new.jsp"), CONTEXT_PATH.concat("/admin-dashboard/themes/new")
    );
    public static final JspPage AD_THEME_UPDATE = new JspPage(
            VIEW_DIR.concat("/admin/theme/update.jsp"), CONTEXT_PATH.concat("/admin-dashboard/themes/%s/update")
    );

    static {
        DEFAULTS = Set.of(
                INDEX,
                SIGN_IN, SIGN_UP,
                PROFILE, UPDATE_PROFILE,
                ADMIN_DASHBOARD,
                AD_STUDENTS,
                AD_TEACHERS, AD_TEACHERS_NEW,
                AD_COURSES, AD_COURSES_NEW, AD_COURSE, AD_COURSE_UPDATE,
                AD_THEMES, AD_THEMES_NEW, AD_THEME_UPDATE
        );
    }

    public static JspPage fromUrl(String url, JspPage defaultPage) {
        if (url == null) return defaultPage;
        return DEFAULTS.stream()
                .filter(jspPage -> jspPage.url.equals(url.strip()))
                .findAny()
                .orElse(notFound(url));
    }

    private static JspPage error(int code, String url) {
        return new JspPage("/".concat(String.valueOf(code)).concat(".jsp"), url);
    }

    public static JspPage notFound(String url) {
        return error(404, url);
    }

    public static JspPage internalServerError(String url) {
        return error(500, url);
    }
}
