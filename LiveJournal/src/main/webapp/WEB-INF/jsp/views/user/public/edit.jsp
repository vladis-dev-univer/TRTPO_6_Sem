<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 16.03.2021
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custag" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.locale" var="rb"/>
<c:choose>
    <c:when test="${not empty publication}">
        <c:set var="publicationId" value="${publication.id}"/>
        <c:set var="name" value="${publication.name}"/>
        <c:set var="content" value="${publication.content}"/>
        <c:set var="genreTitle" value="${publication.genre.title}"/>
        <c:set var="genreId" value="${publication.genre.id}"/>
        <c:set var="publicDate" value="${publication.publicDate}"/>
        <c:set var="title" value="oldEditPublication.name"/>
        <c:set var="publicationName" value="«${publication.name}»"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="newPublication.name"/>
        <c:set var="publicationName" value=""/>
        <c:set var="publicDate" value="${public_date}"/>
    </c:otherwise>
</c:choose>
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
    <c:set var="pageName" value="/user/public/edit.html"/>
    <c:url value="language.html" var="languageUrl"/>
    <ctg:language pageName="${pageName}" languageUrl="${languageUrl}"/>
    <div class="content">
        <div class="public-edit">
            <h3 class="public-edit-title"><fmt:message key="${title}" bundle="${ rb }"/> ${publicationName}</h3>
            <c:url value="/user/public/save.html" var="publicSaveUrl"/>
            <form class="public-edit-container" action="${publicSaveUrl}" method="post">
                <table>
                    <tr>
                        <td><label for="name"><fmt:message key="title.name" bundle="${ rb }"/>:</label></td>
                        <td><input type="text" id="name" name="name" value="${name}"></td>
                    </tr>
                    <tr>
                        <td><label for="genre_title"><fmt:message key="genre.name" bundle="${ rb }"/>:</label></td>
                        <td>
                            <input type="text" id="genre_title" name="genre_title" value="${genreTitle}">
                        </td>
                    </tr>
                    <tr>
                        <td><label for="content"><fmt:message key="content.name" bundle="${ rb }"/>:</label></td>
                        <td><textarea id="content" name="content" rows="20" cols="53">${content}</textarea></td>
                    </tr>
                    <tr>
                        <td><label for="public_date"><fmt:message key="publicationDate.name"
                                                                  bundle="${ rb }"/>:</label></td>
                        <td><input type="text" id="public_date" name="public_date" value="<fmt:formatDate pattern = "yyyy-MM-dd"
                                                    value = "${publicDate}"/>" readonly></td>
                    </tr>
                </table>
                <span style="color:#BA4A00"> ${message}</span>
                <br>
                <form class="save-user">
                    <input type="submit" value="<fmt:message key="userInfoSave.name" bundle="${ rb }"/>">
                    <input type="hidden" name="genre_id" value="${genreId}">
                    <input type="hidden" name="publicationId" value="${publicationId}">
                </form>
            </form>
        </div>
    </div>
</div>

</body>
</html>
