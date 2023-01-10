<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page title="CoursePlatform | Courses">
    <div class="d-flex flex-column align-items-end">
        <form action="${pageContext.request.contextPath}/admin-dashboard/courses/new">
            <button type="submit" class="btn btn-primary">Create new course</button>
        </form>
        <table class="table table-hover table-striped caption-top align-middle">
            <caption>Courses</caption>
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
                <th scope="col">Actions</th>
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
                    <td class="d-flex">
                        <a href="${pageContext.request.contextPath}/admin-dashboard/courses/${course.id}"
                           class="btn btn-link">
                            View
                        </a>
                        <form action="${pageContext.request.contextPath}/admin-dashboard/courses/${course.id}/delete"
                              method="post">
                            <button type="submit" class="btn btn-link">X</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</t:page>