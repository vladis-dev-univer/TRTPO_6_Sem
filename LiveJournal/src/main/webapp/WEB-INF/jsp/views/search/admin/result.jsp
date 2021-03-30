<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 30.03.2021
  Time: 12:04
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
    <c:set var="pageName" value="/search/admin/result.html"/>
    <c:url value="language.html" var="languageUrl"/>
    <ctg:language pageName="${pageName}" languageUrl="${languageUrl}"/>
    <div class="content">
        <div class="search-result-poet">
            <h3 class="search-result-poet-title"><fmt:message key="resultSearchUser.name" bundle="${ rb }"/></h3>
            <c:choose>
                <c:when test="${not empty adminUsersInfo}">
                    <%--Table users--%>
                    <table class="all-poets-table">
                        <thead>
                        <tr>
                            <td><fmt:message key="login.name" bundle="${ rb }"/></td>
                            <td><fmt:message key="userInfoName.name" bundle="${ rb }"/></td>
                            <td><fmt:message key="email.name" bundle="${ rb }"/></td>
                            <td><fmt:message key="dateOfRegistration.name" bundle="${ rb }"/></td>
                                <%--                                <td><fmt:message key="countOfPublication.name" bundle="${ rb }"/></td>--%>
                            <td><fmt:message key="setActivity.name" bundle="${ rb }"/></td>
                        </tr>
                        </thead>
                        <c:forEach items="${adminUsersInfo}" var="userInfo">
                            <tbody>
                            <tr class="active-row">
                                <td>${userInfo.user.login}</td>
                                <td>${userInfo.name}</td>
                                <td>
                                        ${userInfo.user.email}
                                </td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${userInfo.user.dateOfReg}"/></td>
                                <td>${userInfo.user.active}
                                    <c:url value="/admin/user/active.html" var="adminActiveUrl"/>
                                    <form action="${adminActiveUrl}" method="post">
                                        <input type="hidden" name="currentUserId" value="${userInfo.user.id}">
                                        <input type="hidden" name="fromSearch" value="yes">
                                        <input style="margin-top: 8px" class="saveAdmin" type="submit"
                                               value="<fmt:message key="setActivate.name" bundle="${ rb }"/>">
                                    </form>
                                </td>
                            </tr>
                            </tbody>
                        </c:forEach>
                    </table>

                </c:when>
                <c:otherwise>
                    <p><fmt:message key="thereAreNoUsers.name" bundle="${ rb }"/></p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</body>
</html>