<%--
  Created by IntelliJ IDEA.
  User: Вадим
  Date: 02.03.2021
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" language="java"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custag" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.locale" var="rb"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="mainTitle" bundle="${ rb }"/></title>
    <link href="${pageContext.request.contextPath}/css/style.css"
          rel="stylesheet" type="text/css"/>
</head>
<body>

<div class="wrapper">
    <custag:header/>
    <c:set var="pageName" value="/register.html"/>
    <c:url value="language.html" var="languageUrl"/>
    <ctg:language pageName="${pageName}" languageUrl="${languageUrl}"/>
    <div class="content">
        <div class="register">
            <h2 class="register-title"><fmt:message key="registration.title" bundle="${ rb }"/></h2>
            <c:url value="/register.html" var="registerUrl"/>
            <form class="register-container" action="${registerUrl}" method="post">

                <p>
                    <label for="newLogin"><fmt:message key="login.name" bundle="${ rb }"/></label>
                    <input id="newLogin" name="newLogin" type="text">
                </p>

                <p>
                    <label for="newEmail"><fmt:message key="email.name" bundle="${ rb }"/></label>
                    <input id="newEmail" name="newEmail" type="text">
                </p>

                <p>
                    <label for="newPassword"><fmt:message key="password.name" bundle="${ rb }"/></label>
                    <input id="newPassword" name="newPassword" type="password">
                </p>

                <p>
                    <label for="confirmPassword"><fmt:message key="confirmPassword.name" bundle="${ rb }"/></label>
                    <input id="confirmPassword" name="confirmPassword" type="password">
                </p>
                <span style="color:#BA4A00"> ${message}</span>
                <p>
                    <input type="submit" value="<fmt:message key="createMyAccount.name" bundle="${ rb }"/>">
                </p>
            </form>
        </div>
    </div>
</div>

</body>
</html>
