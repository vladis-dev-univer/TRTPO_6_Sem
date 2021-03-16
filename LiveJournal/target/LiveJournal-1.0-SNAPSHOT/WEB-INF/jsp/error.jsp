<%--
  Created by IntelliJ IDEA.
  User: Вадим
  Date: 02.03.2021
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" language="java"%>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.locale" var="rb"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="errorMainTitle" bundle="${ rb }"/></title>
    <link href="${pageContext.request.contextPath}/css/style.css"
          rel="stylesheet" type="text/css"/>
</head>
<body>
<%--    Error page (Unexpected application error)--%>
<div class="wrapper">
    <div class="content">
        <c:choose>
            <c:when test="${not empty error}">
                <h2>${error}</h2>
                <br>
            </c:when>
        </c:choose>
        <a href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="errorBack" bundle="${ rb }"/></a>
    </div>
</div>

</body>
</html>