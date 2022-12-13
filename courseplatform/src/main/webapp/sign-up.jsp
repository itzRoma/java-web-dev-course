<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>CoursePlatform | Sign-up</title>
</head>
<body>
<h1>${requestScope.errorMessage}</h1>
<form action="${pageContext.request.contextPath}/auth/sign-up?redirect-to=${requestScope.redirectTo}" method="post">
    <p>
        <label for="first">First name</label>
        <input id="first" name="first_name" type="text"/>
    </p>

    <p>
        <label for="last">Last name</label>
        <input id="last" name="last_name" type="text"/>
    </p>

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
        <button type="submit">Sign up</button>
    </p>
</form>
</body>
</html>