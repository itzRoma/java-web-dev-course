<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page title="CoursePlatform | Admin dashboard">
    <div class="text-center">
        <div class="row mb-5">
            <div class="col me-5">
                <table class="table table-sm table-hover caption-top">
                    <caption>
                        Recently registered students,
                        <a href="${pageContext.request.contextPath}/admin-dashboard/students"> show all</a>
                    </caption>
                    <thead>
                    <tr>
                        <th scope="col">User ID</th>
                        <th scope="col">Student ID</th>
                        <th scope="col">First name</th>
                        <th scope="col">Last name</th>
                        <th scope="col">Email</th>
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
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col">
                <table class="table table-sm table-hover caption-top">
                    <caption>
                        Recently registered teachers,
                        <a href="${pageContext.request.contextPath}/admin-dashboard/teachers"> show all</a>
                    </caption>
                    <thead>
                    <tr>
                        <th scope="col">User ID</th>
                        <th scope="col">Teacher ID</th>
                        <th scope="col">First name</th>
                        <th scope="col">Last name</th>
                        <th scope="col">Email</th>
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
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="d-flex justify-content-center align-items-center">
            <div class="row w-75">
                <div class="col">
                    <table class="table table-sm table-hover caption-top">
                        <caption>
                            Recently created courses,
                            <a href="${pageContext.request.contextPath}/admin-dashboard/courses"> show all</a>
                        </caption>
                        <thead>
                        <tr>
                            <th scope="col">Course ID</th>
                            <th scope="col">Title</th>
                            <th scope="col">Description</th>
                            <th scope="col">Duration</th>
                            <th scope="col">Min grade</th>
                            <th scope="col">Max grade</th>
                            <th scope="col">Starting date</th>
                            <th scope="col">Status</th>
                        </tr>
                        </thead>
                        <tbody class="table-group-divider">
                        <c:forEach items="${requestScope.courses}" var="course">
                            <tr>
                                <th scope="row">${course.id}</th>
                                <td>${course.title.length() > 20 ? course.title.substring(0, 20).concat("...") : course.title}</td>
                                <td>${course.description.length() > 20 ? course.description.substring(0, 20).concat("...") : course.description}</td>
                                <td>${course.duration}</td>
                                <td>${course.minGrade}</td>
                                <td>${course.maxGrade}</td>
                                <td>${course.startingDate}</td>
                                <td>${course.status}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</t:page>