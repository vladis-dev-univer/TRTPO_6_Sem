<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custag" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.locale" var="rb"/>
<header>
    <div id="menu">
        <ctg:mainTitleTag/>
        <ul id="choice-menu">
            <c:url value="/menu/home.html" var="homeUrl"/>
            <li class="home-href"><a href="${homeUrl}"><fmt:message key="homeHref.title" bundle="${ rb }"/></a></li>

            <c:url value="/menu/poets/list.html" var="poetsUrl"/>
            <li class="poets-href"><a href="${poetsUrl}"><fmt:message key="poetsHref.title" bundle="${ rb }"/></a></li>

            <c:url value="/menu/publications/list.html" var="poetsUrl"/>
            <li class="poets-href"><a href="${poetsUrl}"><fmt:message key="publicationsHref.title" bundle="${ rb }"/></a></li>

            <c:if test="${not empty authorizedUser}">
                <c:forEach items="${menu}" var="item">
                    <c:url value="${item.url}" var="itemUrl"/>
                    <li class="item"><a href="${itemUrl}"><fmt:message key="${item.name}.name" bundle="${ rb }"/></a></li>
                </c:forEach>
            </c:if>

            <c:if test="${sessionScope.authorizedUser == null}">
                <c:url value="/login.html" var="loginUrl"/>
                <li class="login-href"><a href="${loginUrl}"><fmt:message key="loginHref.title" bundle="${ rb }"/></a></li>
            </c:if>
        </ul>
    </div>
</header>
