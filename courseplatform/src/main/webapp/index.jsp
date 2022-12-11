<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>CoursePlatform | Home</title>
</head>
<body>
<h1><%= "Welcome to the CoursePlatform" %></h1><br/>
<p><a href="${pageContext.request.contextPath}/test-db-connection">Test db connection</a></p>
<p><a href="${pageContext.request.contextPath}/auth/sign-up">Sign-up</a></p>
<p><a href="${pageContext.request.contextPath}/sign-in">Sign-in</a></p>
</body>
</html>