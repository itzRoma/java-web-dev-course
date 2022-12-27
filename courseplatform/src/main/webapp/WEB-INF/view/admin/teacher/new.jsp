<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page title="CoursePlatform | New teacher">
    <div class="container">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                <div class="card shadow-2-strong" style="border-radius: 1rem;">
                    <div class="card-body p-5 text-center">

                        <h3>New teacher</h3>

                        <div class="my-3 text-start">
                            <a href="${pageContext.request.contextPath}/admin-dashboard/teachers"
                               class="text-black-50 fw-bold text-decoration-none">
                                &leftarrow; Back
                            </a>
                        </div>

                        <div class="my-3 text-start text-danger">
                                ${requestScope.error}
                        </div>

                        <form class="d-flex flex-column"
                              action="${pageContext.request.contextPath}/admin-dashboard/teachers/new"
                              method="post">

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <input type="text" name="first_name"
                                               class="form-control"
                                               id="floatingFirstName" placeholder="First name" required>
                                        <label for="floatingFirstName">First name</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <input type="text" name="last_name"
                                               class="form-control"
                                               id="floatingLastName" placeholder="Last name" required>
                                        <label for="floatingLastName">Last name</label>
                                    </div>
                                </div>
                            </div>

                            <div class="input-group has-validation mb-3">
                                <div class="form-floating <c:if test="${requestScope.emailError != null}">is-invalid</c:if>">
                                    <input type="email" name="email"
                                           class="form-control <c:if test="${requestScope.emailError != null}">is-invalid</c:if>"
                                           id="floatingEmail" placeholder="Email" required>
                                    <label for="floatingEmail">Email</label>
                                </div>
                                <div class="invalid-feedback text-start">
                                        ${requestScope.emailError}
                                </div>
                            </div>

                            <div class="form-floating mb-3">
                                <input type="password" name="password"
                                       class="form-control"
                                       id="floatingPassword" placeholder="Password" required>
                                <label for="floatingPassword">Password</label>
                            </div>

                            <button class="btn btn-lg btn-primary" type="submit">Create</button>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</t:page>