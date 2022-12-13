<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>CoursePlatform | Sign-in</title>
</head>
<body>
<h1>${requestScope.errorMessage}</h1>
<form action="${pageContext.request.contextPath}/auth/sign-in?redirect-to=${requestScope.redirectTo}" method="post">
    <p>
        <label for="email">Email</label>
        <input id="email" name="email" type="text"/>
        <p>${requestScope.emailMessage}</p>
    </p>

    <p>
        <label for="pass">Password</label>
        <input id="pass" name="password" type="password"/>
    </p>

    <p>
        <button type="submit">Sign in</button>
    </p>
</form>
</body>
</html>