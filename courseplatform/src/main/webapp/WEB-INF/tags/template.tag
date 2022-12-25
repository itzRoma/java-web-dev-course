<%@ tag description="Overall Page template" pageEncoding="utf-8" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="header" fragment="true" %>
<%@ attribute name="footer" fragment="true" %>

<html>
<head>
    <jsp:invoke fragment="head"/>
</head>
<body style="min-height: 100vh; display: flex; flex-direction: column;">
<jsp:invoke fragment="header"/>
<div style="margin: auto 0; padding: 3rem 0;">
    <jsp:doBody/>
</div>
<jsp:invoke fragment="footer"/>
</body>
</html>