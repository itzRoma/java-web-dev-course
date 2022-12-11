<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>CoursePlatform | Sign-in</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/sign-in" method="post">
    <input id="email" type="text"/>
    <label for="email">Email</label>

    <input id="pass" type="password"/>
    <label for="pass">Password</label>

    <button type="submit">Sign in</button>
</form>
</body>
</html>