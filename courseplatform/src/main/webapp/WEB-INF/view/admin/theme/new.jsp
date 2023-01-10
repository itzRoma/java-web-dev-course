<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page title="CoursePlatform | New theme">
    <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-12 col-md-8 col-lg-6 col-xl-5">
            <div class="card shadow-2-strong" style="border-radius: 1rem;">
                <div class="card-body p-5 text-center">

                    <h3>New theme</h3>

                    <div class="my-3 text-start">
                        <a href="${pageContext.request.contextPath}/admin-dashboard/themes"
                           class="text-black-50 fw-bold text-decoration-none">
                            &leftarrow; Back
                        </a>
                    </div>

                    <div class="my-3 text-start text-danger">
                            ${requestScope.error}
                    </div>

                    <form class="d-flex flex-column"
                          action="${pageContext.request.contextPath}/admin-dashboard/themes/new"
                          method="post">

                        <div class="input-group has-validation mb-3">
                            <div class="form-floating <c:if test="${requestScope.nameError != null}">is-invalid</c:if>">
                                <input type="text" name="name"
                                       class="form-control <c:if test="${requestScope.nameError != null}">is-invalid</c:if>"
                                       id="floatingName" required autofocus>
                                <label for="floatingName">Name</label>
                            </div>
                            <div class="invalid-feedback text-start">
                                    ${requestScope.nameError}
                            </div>
                        </div>

                        <button class="btn btn-lg btn-primary" type="submit">Create</button>

                    </form>
                </div>
            </div>
        </div>
    </div>
</t:page>