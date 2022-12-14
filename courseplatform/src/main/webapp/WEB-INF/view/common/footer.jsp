<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="date" class="java.util.Date"/>
<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear"/>

<footer class="bg-light">
    <div class="container text-center p-3">
        &copy;<c:out value="${currentYear}"/> - Roman Bondarenko
    </div>
</footer>