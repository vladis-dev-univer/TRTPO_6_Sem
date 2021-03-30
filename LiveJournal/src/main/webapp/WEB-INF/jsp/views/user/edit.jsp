<%--
  Created by IntelliJ IDEA.
  User: Вадим
  Date: 16.03.2021
  Time: 13:31
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
    <c:when test="${not empty userInfo}">
        <c:set var="surname" value="${userInfo.surname}"/>
        <c:set var="name" value="${userInfo.name}"/>
        <c:set var="pseudonym" value="${userInfo.pseudonym}"/>
        <c:set var="dateOfBirth" value="${userInfo.dateOfBirth}"/>
        <c:set var="title" value="oldUser.name"/>
        <c:set var="userInfoName" value="${userInfo.name}"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="newUser.name"/>
        <c:set var="userInfoName" value=""/>
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
    <c:set var="pageName" value="/user/edit.html"/>
    <c:url value="language.html" var="languageUrl"/>
    <ctg:language pageName="${pageName}" languageUrl="${languageUrl}"/>
    <div class="content">
        <div class="edit-user">
            <h3 class="edit-user-title"><fmt:message key="${title}" bundle="${ rb }"/>${userInfoName}</h3>
            <c:url value="/user/save.html" var="publicSaveUrl"/>
            <form class="edit-user-container" action="${publicSaveUrl}" method="post" enctype="multipart/form-data">
                <table>
                    <tr>
                        <td><label for="name"><fmt:message key="userInfoName.name" bundle="${ rb }"/>:</label></td>
                        <td><input type="text" id="name" name="name" value="${name}"></td>
                    </tr>
                    <tr>
                        <td><label for="surname"><fmt:message key="userInfoSurname.name" bundle="${ rb }"/>:</label></td>
                        <td><input type="text" id="surname" name="surname" value="${surname}"></td>
                    </tr>
                    <tr>
                        <td><label for="pseudonym"><fmt:message key="userInfoPseudonym.name" bundle="${ rb }"/>:</label></td>
                        <td><input type="text" id="pseudonym" name="pseudonym" value="${pseudonym}"></td>
                    </tr>
                    <tr>
                        <td><label for="level"><fmt:message key="userInfoLevel.name" bundle="${ rb }"/>:</label></td>
                        <td><input type="text" id="level" name="level" value="${level}" disabled></td>
                    </tr>
                    <tr>
                        <td><label for="date_of_birth"><fmt:message key="userInfoDateOfBirth.name" bundle="${ rb }"/>:</label></td>
                        <td><input type="date" id="date_of_birth" name="date_of_birth"
                                   value="<fmt:formatDate pattern = "yyyy-MM-dd" value="${dateOfBirth}"/>"
                                   min="1821-01-01" max="2022-01-01"></td>
                    </tr>
                </table>
                <br>
                <form class="editProductClass">
                    <div class="fl_upload" style="text-align: right">
                        <label><input type="file" id="fl_inp" name="pngPath" accept="image/jpeg">choose file</label>
                        <div id="file_name">file not chosen</div>
                    </div>
                </form>
                <br>
                <form class="save-user">
                    <input type="submit" value="<fmt:message key="userInfoSave.name" bundle="${ rb }"/>">
                    <input type="hidden" name="levelName" value="${level}">
                </form>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    <%@include file="/js/main.js" %>
</script>
</body>
</html>