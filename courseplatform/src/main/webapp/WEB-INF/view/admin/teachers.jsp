<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page title="CoursePlatform | Teachers">
    <div class="container text-center">
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
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</t:page>