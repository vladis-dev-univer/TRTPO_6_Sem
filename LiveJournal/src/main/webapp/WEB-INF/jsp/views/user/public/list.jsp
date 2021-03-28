<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 16.03.2021
  Time: 15:07
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
    <c:set var="pageName" value="/user/public/list.html"/>
    <c:url value="language.html" var="languageUrl"/>
    <ctg:language pageName="${pageName}" languageUrl="${languageUrl}"/>
    <div class="content">
        <div class="public-list">
            <h3 class="public-list-title"><fmt:message key="yourPublications.name" bundle="${ rb }"/></h3>
            <c:url value="/user/public/edit.html" var="userPublicationEditUrl"/>
            <c:choose>
            <c:when test="${not empty publications}">
            <table class="publication-table">
                <thead>
                <tr>
                    <td><fmt:message key="title.name" bundle="${ rb }"/></td>
                    <td><fmt:message key="content.name" bundle="${ rb }"/></td>
                    <td><fmt:message key="publicationDate.name" bundle="${ rb }"/></td>
                    <td><fmt:message key="genre.name" bundle="${ rb }"/></td>
                    <td></td><%--<fmt:message key="editPublication.name" bundle="${ rb }"/>--%>
                    <td></td>
                </tr>
                </thead>
                <c:forEach items="${publications}" var="publication">
                    <tbody>
                    <tr class="active-row">
                        <td>${publication.name}</td>
                        <td>
                            <label for="text-area-list"></label>
                            <textarea id="text-area-list" rows="15" cols="40" disabled>${publication.content}</textarea>
                        </td>
                        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${publication.publicDate}"/></td>
                        <td>${publication.genre.title}</td>
                        <td>
                            <form action="${userPublicationEditUrl}" method="post">
                                <input type="hidden" name="newPublic" value="no">
                                <input type="hidden" name="publicationId" value="${publication.id}">
                                <input type="submit" value="<fmt:message key="edit.name" bundle="${ rb }"/>">
                            </form>
                        </td>
                    <td>
                        <c:url value="/user/public/delete.html" var="publicDeleteUrl"/>
                        <form action="${publicDeleteUrl}" method="post">
                            <input type="hidden" name="publicationId" value="${publication.id}">
                            <input type="submit" value="<fmt:message key="publicDelete.name" bundle="${ rb }"/>">
                        </form>
                    </td>
                </tr>
                </tbody>
                </c:forEach>
            </table>
                <ctg:pagination currentPage="${currentPage}" lastPage="${lastPage}" url="${url}"/>
            </c:when>
                <c:otherwise>
                    <p><fmt:message key="thereAreNoPublications.name" bundle="${ rb }"/></p>
                </c:otherwise>
            </c:choose>
            <form action="${userPublicationEditUrl}" method="post">
                <input type="hidden" name="newPublic" value="yes">
                <input type="submit" value="<fmt:message key="addNewPublication.name" bundle="${ rb }"/>">
            </form>
        </div>
    </div>
</div>

</body>
</html>
