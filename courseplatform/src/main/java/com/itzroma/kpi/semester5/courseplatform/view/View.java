package com.itzroma.kpi.semester5.courseplatform.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class View {
    private final JspPage jspPage;
    private final DispatchType dispatchType;

    private String queryParameters = "";

    public void addQueryParameter(String name, String value) {
        if (queryParameters.contains("?".concat(name)) || queryParameters.contains("&".concat(name))) {
            queryParameters = queryParameters.replaceAll(
                    "(?<=".concat(name).concat(").*(?=&)|(?<=").concat(name).concat(").*(?=$)"),
                    "=".concat(value)
            );
            return;
        }

        StringBuilder param = new StringBuilder();
        if (queryParameters.isEmpty()) {
            param.append("?")
                    .append(name)
                    .append("=")
                    .append(value);
        } else {
            param.append("&")
                    .append(name)
                    .append("=")
                    .append(value);
        }
        queryParameters += param.toString();
    }

    public String getJspPath() {
        return jspPage.jspPath();
    }

    public String getUrl() {
        return jspPage.url().concat(queryParameters);
    }

    public static View fromUrl(String url, JspPage defaultPage, DispatchType dispatchType) {
        return new View(JspPage.fromUrl(url, defaultPage), dispatchType);
    }

    public static View notFound(String url, DispatchType dispatchType) {
        return new View(JspPage.notFound(url), dispatchType);
    }

    public static View internalServerError(String url, DispatchType dispatchType) {
        return new View(JspPage.internalServerError(url), dispatchType);
    }
}
