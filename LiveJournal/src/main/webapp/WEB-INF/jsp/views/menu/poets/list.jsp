<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 30.03.2021
  Time: 11:59
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<div class="wrapper">
<custag:header/>
<c:set var="pageName" value="/menu/poets/list.html"/>
<c:url value="language.html" var="languageUrl"/>
<ctg:language pageName="${pageName}" languageUrl="${languageUrl}"/>
<div class="content">
<div class="all-poets">
<h3 class="all-poets-title"><fmt:message key="allPoets.name" bundle="${ rb }"/></h3>
<c:choose>
    <c:when test="${not empty usersInfo}">
        <%--Search Poet--%>
        <br>
        <div class="search-form">
        <c:url value="/search/menu/poet/result.html" var="searchPoetUrl"/>
        <form action="${searchPoetUrl}" method="post">
        <label for="search"></label>
        <input type="text" id="search" name="search" class="search-input"
        placeholder="<fmt:message key="enterNameOfPoet.name" bundle="${ rb }"/>">
        <button type="submit" class="search"><i class="fa fa-search"></i></button>
        </form>
        </div>

        <%--Poets table--%>
        <table class="all-poets-table">
        <thead>
        <tr>
        <td><fmt:message key="userInfoName.name" bundle="${ rb }"/></td>
        <td><fmt:message key="userInfoSurname.name" bundle="${ rb }"/></td>
        <td><fmt:message key="userInfoPseudonym.name" bundle="${ rb }"/></td>
        <td><fmt:message key="userInfoLevel.name" bundle="${ rb }"/></td>
        <td><fmt:message key="userInfoDateOfBirth.name" bundle="${ rb }"/></td>
        <td><fmt:message key="publicationsHref.title" bundle="${ rb }"/></td>
        </tr>
        </thead>
        <c:forEach items="${usersInfo}" var="userInfo">
            <tbody>
            <tr class="active-row">
            <td>${userInfo.name}</td>
            <td>${userInfo.surname}</td>
            <td>${userInfo.pseudonym}</td>
            <td>
            <c:choose>
                <c:when test="${userInfo.level.name.equals('Professional')}">
                    <fmt:message key="levelProfessional.name" bundle="${ rb }"/>
                </c:when>
                <c:when test="${userInfo.level.name.equals('Amateur')}">
                    <fmt:message key="levelAmateur.name" bundle="${ rb }"/>
                </c:when>
                <c:when test="${userInfo.level.name.equals('Beginner')}">
                    <fmt:message key="levelBeginner.name" bundle="${ rb }"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key="levelReader.name" bundle="${ rb }"/>
                </c:otherwise>
            </c:choose>
            </td>
                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${userInfo.dateOfBirth}"/></td>
                <td>
                    <c:url value="/menu/poets/more.html" var="moreUrl"/>
                    <form action="${moreUrl}" method="post">
                        <input type="hidden" name="userInfoId" value="${userInfo.id}">
                        <input type="submit" value="<fmt:message key="publicationListView.name" bundle="${ rb }"/>">
                    </form>
                </td>
            </tr>
            </tbody>
        </c:forEach>
        </table>
        <ctg:pagination currentPage="${currentPage}" lastPage="${lastPage}" url="${url}"/>
    </c:when>
    <c:otherwise>
        <p><fmt:message key="thereAreNoPoets.name" bundle="${ rb }"/></p>
    </c:otherwise>
</c:choose>
</div>
</div>
</div>

</body>
</html>