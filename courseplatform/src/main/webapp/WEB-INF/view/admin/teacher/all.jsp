<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page title="CoursePlatform | Teachers">
    <div class="container d-flex flex-column align-items-end">
        <form action="${pageContext.request.contextPath}/admin-dashboard/teachers/new">
            <button type="submit" class="btn btn-primary">Create new teacher</button>
        </form>
        <table class="table table-hover table-striped caption-top align-middle">
            <caption>Teachers</caption>
            <thead>
            <tr>
                <th scope="col">User ID</th>
                <th scope="col">Teacher ID</th>
                <th scope="col">First name</th>
                <th scope="col">Last name</th>
                <th scope="col">Email</th>
                <th scope="col">Registration date</th>
                <th scope="col">Actions</th>
            </tr>
            </thead>
            <tbody class="table-group-divider">
            <c:forEach items="${requestScope.teachers}" var="teacher">
                <tr>
                    <th scope="row">${teacher.userId}</th>
                    <td>${teacher.teacherId}</td>
                    <td>${teacher.firstName}</td>
                    <td>${teacher.lastName}</td>
                    <td>${teacher.email}</td>
                    <td>${teacher.registrationDate}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin-dashboard/teachers/${teacher.email}"
                           class="btn btn-link">
                            View
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</t:page>