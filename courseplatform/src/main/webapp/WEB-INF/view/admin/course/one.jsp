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
        <div class="d-flex flex-row align-items-center mb-3">
            <strong class="me-2">Units:</strong>
            <form action="${pageContext.request.contextPath}/admin-dashboard/courses/${requestScope.course.id}/units/new"
                  method="post" class="m-0">
                <input type="text" name="unit_title" placeholder="Unit title" required>
                <button type="submit">Add</button>
            </form>
        </div>
        <div class="accordion w-100" id="accordionPanelsStayOpenExample">
            <c:forEach items="${requestScope.course.units}" var="unit">
                <div class="accordion-item">
                    <h2 class="accordion-header d-flex flex-row align-items-center"
                        id="panelsStayOpen-heading${unit.id}">
                        <form action="${pageContext.request.contextPath}/admin-dashboard/courses/${requestScope.course.id}/units/${unit.id}/update"
                              method="post" class="d-flex flex-row align-items-center m-0 me-2 fs-6">
                            <input type="hidden" name="unit_id" value="${unit.id}">
                            <input type="text" name="unit_title" value="${unit.title}" class="border-0">
                            <button type="submit">Update</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/admin-dashboard/courses/${requestScope.course.id}/units/${unit.id}/delete"
                              method="post" class="m-0 fs-6">
                            <input type="hidden" name="unit_id" value="${unit.id}">
                            <button type="submit">Delete</button>
                        </form>
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                data-bs-target="#panelsStayOpen-collapse${unit.id}" aria-expanded="false"
                                aria-controls="panelsStayOpen-collapse${unit.id}"></button>
                    </h2>
                    <div id="panelsStayOpen-collapse${unit.id}" class="accordion-collapse collapse"
                         aria-labelledby="panelsStayOpen-heading${unit.id}">
                        <div class="accordion-body">

                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</t:page>