<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 02.03.2021
  Time: 14:46
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
    <%--        <c:set var="pageName" value="/login.html"/>--%>
    <%--        <c:url value="language.html" var="languageUrl"/>--%>
    <%--        <ctg:language pageName="${pageName}" languageUrl="${languageUrl}"/>--%>
    <div class="content">
        <div class="login">
            <h2 class="login-title"><fmt:message key="signIn.name" bundle="${ rb }"/></h2>
            <c:url value="/login.html" var="loginUrl"/>
            <form class="login-container" action="${loginUrl}" method="post">
                <p>
                    <label for="login"></label>
                    <input type="text" id="login" name="login"
                           placeholder="<fmt:message key="login.name" bundle="${ rb }"/>">
                </p>
                <p>
                    <label for="password"></label>
                    <input type="password" id="password" name="password" placeholder="<fmt:message key="password.name" bundle="${ rb }"/>">
                </p>
                <span style="color:#BA4A00"> ${message}</span>
                <p>
                    <input type="submit" value="<fmt:message key="log_in.name" bundle="${ rb }"/>">
                </p>

                <c:url value="/register.html" var="registerUrl"/>
                <p class="login_signup">
                    <fmt:message key="createAccountQuestion" bundle="${ rb }"/>
                    <a href="${registerUrl}">
                        <fmt:message key="registration.title" bundle="${ rb }"/>
                    </a>
                </p>

            </form>
        </div>
    </div>
</div>

</body>
</html>
