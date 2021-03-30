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
</head>
<body>
<div class="wrapper">
    <custag:header/>
    <c:set var="pageName" value="/menu/poets/more.html"/>
    <c:url value="language.html" var="languageUrl"/>
    <ctg:language pageName="${pageName}" languageUrl="${languageUrl}"/>
    <div class="content">
        <div class="more">
            <h3 class="more-title">"${userInfo.pseudonym}"<br><fmt:message key="allPublications.name" bundle="${ rb }"/></h3>
            <c:choose>
                <c:when test="${not empty publications}">
                    <table class="publication-table">
                        <thead>
                        <tr>
                            <td><fmt:message key="title.name" bundle="${ rb }"/></td>
                            <td><fmt:message key="content.name" bundle="${ rb }"/></td>
                            <td><fmt:message key="publicationDate.name" bundle="${ rb }"/></td>
                            <td><fmt:message key="genre.name" bundle="${ rb }"/></td>
                        </tr>
                        </thead>
                        <c:forEach items="${publications}" var="publication">
                            <tbody>
                            <tr class="active-row">
                                <td>${publication.name}</td>
                                <td>
                                    <label for="text-area-list"></label>
                                    <textarea id="text-area-list" rows="15" cols="40" disabled>
                                            ${publication.content}
                                    </textarea>
                                </td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${publication.publicDate}"/></td>
                                <td>${publication.genre.title}</td>
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
        </div>
    </div>
</div>

</body>
</html>