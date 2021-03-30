<%--
  Created by IntelliJ IDEA.
  User: Вадим
  Date: 30.03.2021
  Time: 12:14
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
    <c:set var="pageName" value="/menu/publications/list.html"/>
    <c:url value="language.html" var="languageUrl"/>
    <ctg:language pageName="${pageName}" languageUrl="${languageUrl}"/>
    <div class="content">
        <div class="all-public-list">
            <h3 class="all-public-list-title"><fmt:message key="allPublications.name" bundle="${ rb }"/></h3>
            <c:choose>
            <c:when test="${not empty usersInfo}">
                <%--Search publication--%>
            <br>
            <div class="search-form">
                <c:url value="/search/menu/publication/result.html" var="searchPublicationUrl"/>
                <form action="${searchPublicationUrl}" method="post">
                    <label for="search"></label>
                    <input type="text" id="search" name="search" class="search-input"
                           placeholder="<fmt:message key="enterNameOfPublication.name" bundle="${ rb }"/>">
                    <button type="submit" class="search"><i class="fa fa-search"></i></button>
                </form>
            </div>
                <%--Publications table--%>
                <table class="all-publication-table">
                    <thead>
                    <tr>
                        <td><fmt:message key="userInfoPseudonym.name" bundle="${ rb }"/></td>
                        <td><fmt:message key="title.name" bundle="${ rb }"/></td>
                        <td><fmt:message key="content.name" bundle="${ rb }"/></td>
                        <td><fmt:message key="publicationDate.name" bundle="${ rb }"/></td>
                        <td><fmt:message key="genre.name" bundle="${ rb }"/></td>
                        <c:choose>
                            <c:when test="${not empty authorizedUser}">
                                <td><fmt:message key="comments.name" bundle="${ rb }"/></td>
                            </c:when>
                        </c:choose>
                    </tr>
                    </thead>
                    <c:forEach items="${usersInfo}" var="userInfo">
                        <c:forEach items="${userInfo.user.publications}" var="publication">
                            <tbody>
                            <tr class="active-row">
                                <td>${userInfo.pseudonym}</td>
                                <td>${publication.name}</td>
                                <td>
                                    <label for="text-area-list"></label>
                                    <textarea id="text-area-list" rows="15" cols="40" disabled>
                                            ${publication.content}
                                    </textarea>
                                </td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${publication.publicDate}"/></td>
                                <td>${publication.genre.title}</td>
                                <c:choose>
                                    <c:when test="${not empty authorizedUser}">
                                        <td>
                                            <c:url value="/menu/publications/comment.html" var="commentUrl"/>
                                            <form action="${commentUrl}" method="post">
                                                <input type="hidden" name="publicationId" value="${publication.id}">
                                                <input type="hidden" name="publication" value="${publication}">
                                                <input type="submit" value="<fmt:message key="publicationListView.name" bundle="${ rb }"/>">
                                            </form>
                                        </td>
                                    </c:when>
                                </c:choose>
                            </tr>
                            </tbody>
                        </c:forEach>
                    </c:forEach>
                </table>
                <ctg:pagination currentPage="${currentPage}" lastPage="${lastPage}" url="${url}"/>
            </c:when>
                <c:otherwise>
                    <p><fmt:message key="thereAreNoPublications.name" bundle="${ rb }"/></p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

</body>
</html>
