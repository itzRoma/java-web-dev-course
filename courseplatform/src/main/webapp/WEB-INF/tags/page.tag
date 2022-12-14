<%@ tag description="Page tag" pageEncoding="utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="title" required="true" type="java.lang.String" %>

<t:template>
    <jsp:attribute name="head">
        <meta charset="utf-8"/>
        <title>${title}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
        <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery-3.6.2.min.js"></script>
    </jsp:attribute>

    <jsp:attribute name="header">
        <jsp:include page="/WEB-INF/view/common/header.jsp"/>
    </jsp:attribute>

    <jsp:attribute name="footer">
        <jsp:include page="/WEB-INF/view/common/footer.jsp"/>
    </jsp:attribute>

    <jsp:body>
        <jsp:doBody/>
    </jsp:body>
</t:template>