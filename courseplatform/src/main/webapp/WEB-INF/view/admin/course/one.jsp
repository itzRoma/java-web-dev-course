<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page title="CoursePlatform | Courses">
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/admin-dashboard/courses"
           class="text-black-50 fw-bold text-decoration-none">
            &leftarrow; Back
        </a>
    </div>
    <div class="d-flex flex-column align-items-start">
        <p><strong>ID:</strong> ${requestScope.course.id}</p>
        <p><strong>Title:</strong> ${requestScope.course.title}</p>
        <p><strong>Description:</strong> ${requestScope.course.description}</p>
        <p><strong>Duration:</strong> ${requestScope.course.duration} hour(s)</p>
        <p><strong>Min grade:</strong> ${requestScope.course.minGrade}</p>
        <p><strong>Max grade:</strong> ${requestScope.course.maxGrade}</p>
        <p><strong>Starting date:</strong> ${requestScope.course.startingDate}</p>
        <p><strong>Status:</strong> ${requestScope.course.status}</p>
        <p><strong>Themes:</strong> ${requestScope.course.themes.stream().map(t -> t.name).toList()}</p>
    </div>
</t:page>