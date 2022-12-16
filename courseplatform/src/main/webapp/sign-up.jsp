<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page title="CoursePlatform | Sign-up">
    <div class="container">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                <div class="card shadow-2-strong" style="border-radius: 1rem;">
                    <div class="card-body p-5 text-center">

                        <h3>Sign up</h3>

                        <div class="my-3 text-start">&leftarrow;
                            <a href="${requestScope.redirectTo}" class="text-black-50 fw-bold">Back</a>
                        </div>

                        <div class="my-3 invalid-feedback text-start">
                                ${requestScope.error}
                        </div>

                        <form class="d-flex flex-column"
                              action="${pageContext.request.contextPath}/auth/sign-up?redirect-to=${requestScope.redirectTo}"
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

                            <input type="hidden" name="is-admin" value="${requestScope.isAdmin}">

                            <button class="btn btn-lg btn-primary" type="submit">Sign up</button>

                        </form>

                        <hr class="my-4">

                        <p class="mb-0">Have an account?
                            <a href="${pageContext.request.contextPath}/auth/sign-in?redirect-to=${requestScope.redirectTo}"
                               class="text-black-50 fw-bold">Sign In</a>
                        </p>

                    </div>
                </div>
            </div>
        </div>
    </div>
</t:page>