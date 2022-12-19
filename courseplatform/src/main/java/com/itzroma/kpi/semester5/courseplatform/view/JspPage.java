package com.itzroma.kpi.semester5.courseplatform.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record JspPage(String jspPath, String url) {
    private static final String CONTEXT_PATH = "/courseplatform";
    private static final String VIEW_DIR = "/WEB-INF/view";

    private static final List<JspPage> DEFAULTS = new ArrayList<>();

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

    static {
        DEFAULTS.addAll(Arrays.asList(
                INDEX,
                SIGN_IN, SIGN_UP,
                PROFILE
        ));
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
