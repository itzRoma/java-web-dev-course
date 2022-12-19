<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page title="CoursePlatform | Profile">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col col-lg-9 col-xl-7">
                <div class="card">
                    <div class="rounded-top text-white d-flex flex-row" style="background-color: #000; height:200px;">
                        <div class="ms-4 mt-5 d-flex flex-column" style="width: 150px;">
                            <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-profiles/avatar-1.webp"
                                 alt="Generic placeholder image"
                                 class="img-fluid img-thumbnail mt-4 mb-2"
                                 style="width: 150px; z-index: 1">
                            <a href="${pageContext.request.contextPath}/profile/update"
                               class="btn btn-outline-dark"
                               data-mdb-ripple-color="dark"
                               style="z-index: 1;">Update profile</a>
                        </div>
                        <div class="ms-3" style="margin-top: 130px;">
                            <h5>${requestScope.user.firstName} ${requestScope.user.lastName}</h5>
                            <p>${requestScope.user.role}</p>
                        </div>
                    </div>
                    <div class="p-4 text-black" style="background-color: #f8f9fa;">
                        <div class="d-flex justify-content-end text-center py-1">
                            <div>
                                <p class="mb-1 h5">1</p>
                                <p class="small text-muted mb-0">Enrolled</p>
                            </div>
                            <div class="px-3">
                                <p class="mb-1 h5">1</p>
                                <p class="small text-muted mb-0">In-progress</p>
                            </div>
                            <div>
                                <p class="mb-1 h5">1</p>
                                <p class="small text-muted mb-0">Completed</p>
                            </div>
                        </div>
                    </div>
                    <div class="card-body p-4 text-black">
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <p class="lead fw-normal mb-0">Courses overview</p>
                        </div>
                        <div class="list-group">
                            <a href="${pageContext.request.contextPath}/courses/1"
                               class="list-group-item list-group-item-action">
                                <div class="d-flex w-100 justify-content-between">
                                    <h5 class="mb-1">Course #1</h5>
                                    <small>Enrolling</small>
                                </div>
                                <p class="mb-1">Some placeholder content in a paragraph.</p>
                            </a>
                            <a href="${pageContext.request.contextPath}/courses/2"
                               class="list-group-item list-group-item-action">
                                <div class="d-flex w-100 justify-content-between">
                                    <h5 class="mb-1">Course #2</h5>
                                    <small>In-progress</small>
                                </div>
                                <p class="mb-1">Some placeholder content in a paragraph.</p>
                            </a>
                            <a href="${pageContext.request.contextPath}/courses/3"
                               class="list-group-item list-group-item-action">
                                <div class="d-flex w-100 justify-content-between">
                                    <h5 class="mb-1">Course #3</h5>
                                    <small>Completed</small>
                                </div>
                                <p class="mb-1">Some placeholder content in a paragraph.</p>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</t:page>