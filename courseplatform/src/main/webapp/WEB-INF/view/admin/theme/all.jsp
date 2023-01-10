<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page title="CoursePlatform | Themes">
    <div class="d-flex flex-column">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <a href="${pageContext.request.contextPath}/admin-dashboard"
               class="text-black-50 fw-bold text-decoration-none">
                &leftarrow; Back
            </a>
            <form action="${pageContext.request.contextPath}/admin-dashboard/themes/new" class="m-0">
                <button type="submit" class="btn btn-primary">Create new theme</button>
            </form>
        </div>
        <table class="table table-hover table-striped caption-top align-middle">
            <caption>Themes</caption>
            <thead>
            <tr>
                <th scope="col">Theme ID</th>
                <th scope="col">Name</th>
                <th scope="col">Number of uses</th>
                <th scope="col">Actions</th>
            </tr>
            </thead>
            <tbody class="table-group-divider">
            <c:forEach items="${requestScope.themes}" var="theme">
                <tr>
                    <th scope="row">${theme.id}</th>
                    <td>${theme.name}</td>
                    <td>${theme.numberOfUses}</td>
                    <td class="d-flex">
                        <a href="${pageContext.request.contextPath}/admin-dashboard/themes/${theme.name}/update"
                           class="btn btn-link">
                            Update
                        </a>
                        <form action="${pageContext.request.contextPath}/admin-dashboard/themes/${theme.name}/delete"
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