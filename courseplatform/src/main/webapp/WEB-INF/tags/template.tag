<%@ tag description="Overall Page template" pageEncoding="utf-8" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="header" fragment="true" %>
<%@ attribute name="footer" fragment="true" %>

<html>
<head>
    <jsp:invoke fragment="head"/>
</head>
<body class="min-vh-100 d-flex flex-column">
<jsp:invoke fragment="header"/>
<main class="my-auto py-5 container">
    <jsp:doBody/>
</main>
<jsp:invoke fragment="footer"/>
</body>
</html>