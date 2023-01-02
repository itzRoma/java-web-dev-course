<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page title="CoursePlatform | New teacher">
    <div class="container">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                <div class="card shadow-2-strong" style="border-radius: 1rem;">
                    <div class="card-body p-5 text-center">

                        <h3>New course</h3>

                        <div class="my-3 text-start">
                            <a href="${pageContext.request.contextPath}/admin-dashboard/courses"
                               class="text-black-50 fw-bold text-decoration-none">
                                &leftarrow; Back
                            </a>
                        </div>

                        <div class="my-3 text-start text-danger">
                                ${requestScope.error}
                        </div>

                        <form class="d-flex flex-column"
                              action="${pageContext.request.contextPath}/admin-dashboard/courses/new"
                              method="post">

                            <div class="form-floating mb-3">
                                <input type="text" name="title"
                                       class="form-control"
                                       id="floatingTitle" placeholder="Title" required autofocus>
                                <label for="floatingTitle">Title</label>
                            </div>

                            <div class="form-floating mb-3">
                                <textarea class="form-control" placeholder="Description"
                                          id="floatingDescription" name="description" required></textarea>
                                <label for="floatingDescription">Description</label>
                            </div>

                            <div class="form-floating mb-3">
                                <input type="number" pattern="^[1-9][0-9]*$" name="duration"
                                       class="form-control"
                                       id="floatingDuration" placeholder="Duration" required>
                                <label for="floatingDuration">Duration</label>
                            </div>

                            <div class="input-group has-validation mb-3">
                                <div class="form-floating <c:if test="${requestScope.dateError != null}">is-invalid</c:if>">
                                    <input type="datetime-local" name="starting_date"
                                           class="form-control <c:if test="${requestScope.dateError != null}">is-invalid</c:if>"
                                           id="floatingStartingDate" required>
                                    <label for="floatingStartingDate">Starting date</label>
                                </div>
                                <div class="invalid-feedback text-start">
                                        ${requestScope.dateError}
                                </div>
                            </div>

                            <button class="btn btn-lg btn-primary" type="submit">Create</button>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</t:page>