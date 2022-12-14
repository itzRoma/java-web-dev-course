<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar bg-light">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}">CoursePlatform</a>
        <div class="d-flex gap-2">
            <a class="btn btn-link" href="${pageContext.request.contextPath}/test-db-connection" role="button">
                Test DB connection
            </a>

            <c:choose>
                <c:when test="${sessionScope.email == null}">
                    <a class="btn btn-outline-primary"
                       href="${pageContext.request.contextPath}/auth/sign-up?redirect-to=..."
                       role="button">Sign-up</a>

                    <a class="btn btn-primary"
                       href="${pageContext.request.contextPath}/auth/sign-in?redirect-to=..."
                       role="button">Sign-in</a>
                </c:when>
                <c:otherwise>
                    <div class="btn-group">
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/profile" role="button">
                            Profile
                        </a>
                        <button type="button"
                                class="btn btn-primary dropdown-toggle dropdown-toggle-split"
                                data-bs-toggle="dropdown"
                                aria-expanded="false">
                            <span class="visually-hidden">Toggle Dropdown</span>
                        </button>

                        <ul class="dropdown-menu">
                            <li>
                                <form class="m-0"
                                      action="${pageContext.request.contextPath}/auth/sign-out?redirect-to=..."
                                      method="post">
                                    <button type="submit" class="btn">Sign out</button>
                                </form>
                            </li>
                        </ul>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

<script>
    let redirectTo = window.location.href.substring(window.location.origin.length)
    redirectTo = redirectTo.includes("?") ? redirectTo.substring(0, redirectTo.indexOf("?")) : redirectTo

    const regex = /(?<=redirect-to).*(?=&)|(?<=redirect-to).*(?=$)/;

    $("a").each(function () {
        this.href = this.href.match(regex) ? this.href.replace(regex, "=" + redirectTo) : this.href
    })

    $("form").each(function () {
        this.action = this.action.match(regex) ? this.action.replace(regex, "=" + redirectTo) : this.action
    })
</script>