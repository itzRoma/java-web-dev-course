<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>CoursePlatform | Home</title>
</head>
<body>
<h1><%= "Welcome to the CoursePlatform" %></h1><br/>
<p><a href="${pageContext.request.contextPath}/test-db-connection">Test db connection</a></p>
<%
    if (request.getSession() != null && request.getSession().getAttribute("email") != null) {
        out.print("<p><a href=" + request.getContextPath() + "/auth/sign-out?redirect-to=" + request.getRequestURI() + ">Sign-out</a></p>");
    } else {
        out.print(
                "<p><a href=" + request.getContextPath() + "/auth/sign-up?redirect-to=" + request.getRequestURI() + ">Sign-up</a></p>" +
                "<p><a href=" + request.getContextPath() + "/auth/sign-in?redirect-to=" + request.getRequestURI() + ">Sign-in</a></p>"
        );
    }
%>
</body>
</html>