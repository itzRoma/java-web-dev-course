<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page title="CoursePlatform | Students">
    <table class="table table-hover table-striped caption-top align-middle">
        <caption>Students</caption>
        <thead>
        <tr>
            <th scope="col">User ID</th>
            <th scope="col">Student ID</th>
            <th scope="col">First name</th>
            <th scope="col">Last name</th>
            <th scope="col">Email</th>
            <th scope="col">Registration date</th>
            <th scope="col">Blocked</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody class="table-group-divider">
        <c:forEach items="${requestScope.students}" var="student">
            <tr>
                <th scope="row">${student.userId}</th>
                <td>${student.studentId}</td>
                <td>${student.firstName}</td>
                <td>${student.lastName}</td>
                <td>${student.email}</td>
                <td>${student.registrationDate}</td>
                <td>${student.blocked}</td>
                <td class="d-flex">
                    <a href="${pageContext.request.contextPath}/admin-dashboard/students/${student.email}"
                       class="btn btn-link">
                        View
                    </a>
                    <form action="${pageContext.request.contextPath}/admin-dashboard/students/${student.email}/toggle-block"
                          method="post">
                        <button type="submit" class="btn btn-link">
                            <c:choose>
                                <c:when test="${student.blocked}">
                                    Unblock
                                </c:when>
                                <c:otherwise>
                                    Block
                                </c:otherwise>
                            </c:choose>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</t:page>