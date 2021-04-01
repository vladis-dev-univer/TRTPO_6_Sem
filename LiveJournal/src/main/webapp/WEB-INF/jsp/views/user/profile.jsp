<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 02.03.2021
  Time: 19:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custag" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.locale" var="rb"/>
<c:choose>
    <c:when test="${authorizedUser.active == true}">
        <c:set var="active" value="activeYes.name"/>
    </c:when>
    <c:otherwise>
        <c:set var="active" value="activeNo.name"/>
    </c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${authorizedUser.role.name.equals('User')}">
        <c:set var="role" value="roleUser.name"/>
    </c:when>
    <c:otherwise>
        <c:set var="role" value="roleAdmin.name"/>
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
    <c:set var="pageName" value="/user/profile.html"/>
    <c:url value="language.html" var="languageUrl"/>
    <ctg:language pageName="${pageName}" languageUrl="${languageUrl}"/>

<%--    <c:url value="/user-info-image" var="UserInfoImage"/>--%>
    <div class="content">
        <div class="profile">
            <h3 class="profile-title"><fmt:message key="${role}" bundle="${ rb }"/></h3>
            <form class="profile-container">
<%--                <img class="productImages" src="${UserInfoImage}?path=${userInfo.imgPath}"/>--%>
                <table>
                    <tr>
                        <td><label for="login"><fmt:message key="login.name" bundle="${ rb }"/>:</label></td>
                        <td><input type="text" id="login" value="${authorizedUser.login}" disabled></td>
                    </tr>
                    <tr>
                        <td><label for="email"><fmt:message key="email.name" bundle="${ rb }"/>:</label></td>
                        <td><input type="text" id="email" value="${authorizedUser.email}" disabled></td>
                    </tr>
                    <tr>
                        <td><label for="date_of_reg"><fmt:message key="dateOfRegistration.name"
                                                                  bundle="${ rb }"/></label></td>
                        <td><input type="text" id="date_of_reg" value="<fmt:formatDate pattern = "yyyy-MM-dd"
                             value = "${authorizedUser.dateOfReg}" />" disabled></td>
                    </tr>
                    <tr>
                        <td><label for="is_active"><fmt:message key="active.name" bundle="${ rb }"/></label></td>
                        <td><input type="text" id="is_active" value="<fmt:message key="${active}" bundle="${ rb }"/>"
                                   disabled></td>
                    </tr>
                </table>
            </form>
            <div class="buttons-profile">
                <table>
                    <tr>
                        <td>
                            <c:url value="/user/edit.html" var="userEditUrl"/>
                            <form class="edit" action="${userEditUrl}" method="post">
                                <input type="submit" value="<fmt:message key="information.name" bundle="${ rb }"/>">
                            </form>
                        </td>
                        <td>
                            <c:url value="/user/public/list.html" var="userListUrl"/>
                            <form class="public" action="${userListUrl}" method="post">
                                <input type="hidden" name="authorizedUserId" value="${authorizedUser.id}">
                                <input type="submit" value="<fmt:message key="myPublications.name" bundle="${ rb }"/>">
                            </form>
                        </td>
                        <td>
                            <c:url value="/logout.html" var="logoutUrl"/>
                            <form class="logout" action="${logoutUrl}" method="post">
                                <input type="submit" value="<fmt:message key="log_out.name" bundle="${ rb }"/>">
                            </form>
                        </td>
                    </tr>
                </table>
            </div>

        </div>
    </div>
</div>

</body>
</html>