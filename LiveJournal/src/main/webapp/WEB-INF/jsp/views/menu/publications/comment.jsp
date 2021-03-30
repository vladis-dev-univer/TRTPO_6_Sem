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
</head>
<body>
<div class="wrapper">
    <custag:header/>
    <c:set var="pageName" value="/menu/publications/comment.html"/>
    <c:url value="language.html" var="languageUrl"/>
    <ctg:language pageName="${pageName}" languageUrl="${languageUrl}"/>
    <div class="content">
        <div class="comment">
            <h3 class="comment-title"><fmt:message key="comments.name" bundle="${ rb }"/><br>"${publication.name}"</h3>
            <c:url value="/user/public/comments/edit.html" var="editCommentUrl"/>
            <c:choose>
            <c:when test="${authorizedUser.active eq true}">
            <c:choose>
            <c:when test="${not empty publicationComments}">
            <c:forEach items="${publicationComments}" var="publicationComment">
            <table class="publication-table">
                <thead>
                <tr>
                    <td><fmt:formatDate pattern="yyyy-MM-dd"
                                        value="${publicationComment.commentDate}"/>
                        - ${publicationComment.userInfo.name}</td>
                    <c:choose>
                        <c:when
                                test="${publicationComment.userInfo.user.id == sessionScope.authorizedUser.id}">
                            <td></td><%--<fmt:message key="changeComment.name" bundle="${ rb }"/>--%>
                            <td></td>
                        </c:when>
                    </c:choose>
                </tr>
                </thead>
                <tbody>
                <tr class="active-row">
                    <td>
                        <label for="text-comment"></label>
                        <textarea rows="3" cols="40" id="text-comment" name="text-comment"
                                  disabled>
                                ${publicationComment.text}
                        </textarea>
                    </td>
                    <c:choose>
                    <c:when
                            test="${publicationComment.userInfo.user.id == sessionScope.authorizedUser.id}">
                    <td>
                        <form action="${editCommentUrl}" method="post">
                            <input type="hidden" name="newComment" value="no">
                            <input type="hidden" name="publicCommentId"
                                   value="${publicationComment.id}">
                            <input type="submit"
                                   value="<fmt:message key="edit.name" bundle="${ rb }"/>">
                        </form>
                    </td>
                        <td>
                            <c:url value="/user/public/comments/delete.html"
                                   var="deleteCommentUrl"/>
                            <form action="${deleteCommentUrl}" method="post">
                                <input type="hidden" name="publicCommentId"
                                       value="${publicationComment.id}">
                                <input type="submit"
                                       value="<fmt:message key="publicDelete.name" bundle="${ rb }"/>">
                            </form>
                        </td>
                    </c:when>
                    </c:choose>
                </tr>
                </tbody>
            </table>
            </c:forEach>
            </c:when>
                <c:otherwise>
                    <p><fmt:message key="thereAreNoPublicationComments.name" bundle="${ rb }"/></p>
                </c:otherwise>
            </c:choose>
                <form action="${editCommentUrl}" method="post">
                    <input type="hidden" name="newComment" value="yes">
                    <input type="hidden" name="publicationId" value="${publication.id}">
                    <input style="margin: auto" type="submit"
                           value="<fmt:message key="addNewComment.name" bundle="${ rb }"/>">
                </form>
            </c:when>
                <c:otherwise>
                    <p style="margin: auto; color:#BA4A00"><fmt:message key="deactivateComment.name" bundle="${ rb }"/></p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

</body>
</html>
