<%--
  Created by IntelliJ IDEA.
  User: Вадим
  Date: 16.03.2021
  Time: 13:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" language="java" %>
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
    <c:set var="pageName" value="/menu/home.html"/>
    <c:url value="language.html" var="languageUrl"/>
    <ctg:language pageName="${pageName}" languageUrl="${languageUrl}"/>
    <div class="content">
        <div class="home">
            <form class="home-container">
                <br>
                <h1><fmt:message key="homeH1.title" bundle="${ rb }"/></h1>
                <div class="welcome">
                    <div>
                        <img alt="logo" src="${pageContext.request.contextPath}/img/PoetryLike-logo.png"
                             width="300" height="300" style="float:right; margin-right: 120px">
                    </div>
                    <div style="margin-left: 40px">
                        <p style="text-indent: 2em"><fmt:message key="homeP.text" bundle="${ rb }"/></p>
                        <p style="text-indent: 2em"><fmt:message key="homePWel.text" bundle="${ rb }"/></p>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>