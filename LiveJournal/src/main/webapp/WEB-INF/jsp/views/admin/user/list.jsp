<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 16.03.2021
  Time: 15:09
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<div class="wrapper">
    <custag:header/>
    <c:set var="pageName" value="/admin/user/list.html"/>
    <c:url value="language.html" var="languageUrl"/>
    <ctg:language pageName="${pageName}" languageUrl="${languageUrl}"/>
    <div class="content">
        <div class="admin-user-list">
            <h3 class="admin-user-list-title"><fmt:message key="allUsers.name" bundle="${ rb }"/></h3>
            <c:choose>
            <c:when test="${not empty adminUsersInfo}">
                <%--Search user--%>
            <br>
            <div class="search-form">
                <c:url value="/search/admin/result.html" var="searchAdminUrl"/>
                <form action="${searchAdminUrl}" method="post">
                    <label for="search"></label>
                    <input type="text" id="search" name="search" class="search-input"
                           placeholder="<fmt:message key="enterNameForSearchAdmin.name" bundle="${ rb }"/>">
                    <button type="submit" class="search"><i class="fa fa-search"></i></button>
                </form>
            </div>
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
                                    <input type="hidden" name="fromSearch" value="no">
                                    <input style="margin-top: 8px" class="saveAdmin" type="submit"
                                           value="<fmt:message key="setActivate.name" bundle="${ rb }"/>">
                                </form>
                            </td>
                        </tr>
                        </tbody>
                    </c:forEach>
                </table>
                <ctg:pagination currentPage="${currentPage}" lastPage="${lastPage}" url="${url}"/>
            </c:when>
                <c:otherwise>
                    <p><fmt:message key="thereAreNoUsers.name" bundle="${ rb }"/></p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

</body>
</html>
