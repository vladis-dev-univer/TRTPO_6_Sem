<%--
  Created by IntelliJ IDEA.
  User: Вадим
  Date: 30.03.2021
  Time: 12:17
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
    <c:set var="pageName" value="/search/menu/poet/result.html"/>
    <c:url value="language.html" var="languageUrl"/>
    <ctg:language pageName="${pageName}" languageUrl="${languageUrl}"/>
    <div class="content">
        <div class="search-result-poet">
            <h3 class="search-result-poet-title"><fmt:message key="resultSearchPoet.name" bundle="${ rb }"/></h3>
            <c:choose>
            <c:when test="${not empty poets}">
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
                <c:forEach items="${poets}" var="poet">
                <tbody>
                <tr class="active-row">
                    <td>${poet.name}</td>
                    <td>${poet.surname}</td>
                    <td>${poet.pseudonym}</td>
                    <td>
                        <c:choose>
                            <c:when test="${poet.level.name.equals('Professional')}">
                                <fmt:message key="levelProfessional.name" bundle="${ rb }"/>
                            </c:when>
                            <c:when test="${poet.level.name.equals('Amateur')}">
                                <fmt:message key="levelAmateur.name" bundle="${ rb }"/>
                            </c:when>
                            <c:when test="${poet.level.name.equals('Beginner')}">
                                <fmt:message key="levelBeginner.name" bundle="${ rb }"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="levelReader.name" bundle="${ rb }"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><fmt:formatDate pattern="yyyy-MM-dd" value="${poet.dateOfBirth}"/></td>
                    <td>
                            <c:url value="/menu/poets/more.html" var="moreUrl"/>
                        <form action="${moreUrl}" method="post">
                            <input type="hidden" name="userInfoId" value="${poet.id}">
                            <input type="submit" value="<fmt:message key="publicationListView.name" bundle="${ rb }"/>">
                        </form>
                    </td>
                </tr>
                </tbody>
                </c:forEach>
            </table>
                <c:choose>
                    <c:when test="${paginationChoice eq 1}">
                        <ctg:pagination currentPage="${currentPage}" lastPage="${lastPage}" url="${url}"/>
                    </c:when>
                </c:choose>

            </c:when>
                <c:otherwise>
                    <p><fmt:message key="thereAreNoFoundPoets.name" bundle="${ rb }"/></p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</body>
</html>
