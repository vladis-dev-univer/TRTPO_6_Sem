<%--
  Created by IntelliJ IDEA.
  User: Вадим
  Date: 30.03.2021
  Time: 12:14
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
    <c:when test="${not empty publicationComment}">
        <c:set var="text" value="${publicationComment.text}"/>
        <c:set var="commentDate" value="${publicationComment.commentDate}"/>
        <c:set var="commentId" value="${publicationComment.id}"/>
    </c:when>
    <c:otherwise>
        <c:set var="commentDate" value="${publicationCommentDate}"/>
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
        <c:set var="pageName" value="/user/public/comments/edit.html"/>
        <c:url value="language.html" var="languageUrl"/>
        <ctg:language pageName="${pageName}" languageUrl="${languageUrl}"/>
        <div class="content">
            <div class="comment-change">

                <br>
                <br>
                <c:url value="/user/public/comments/save.html" var="publicCommentSaveUrl"/>
                <form class="public-comment-edit-container" action="${publicCommentSaveUrl}" method="post">
                    <table>
                        <tr>
                            <td>
                                <label style="margin-left: 10px;" for="public_comment_date">
                                    <fmt:message key="publicationCommentDate.name" bundle="${ rb }"/>:</label>
                                <input style="margin-left: 10px;" type="text" id="public_comment_date"
                                       name="public_comment_date"
                                       value="<fmt:formatDate pattern = "yyyy-MM-dd" value = "${commentDate}"/>"
                                       readonly>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label style="margin-left: 10px;" for="public-comment-text">
                                    <fmt:message key="commentText.name" bundle="${ rb }"/>:<br></label>
                                <textarea rows="3" cols="40" id="public-comment-text"
                                          name="public_comment_text">${text}</textarea>
                            </td>
                        </tr>
                    </table>
                    <br>
                    <form class="comment-save">
                        <input type="hidden" name="publicId" value="${publicId}">
                        <input type="hidden" name="publicationCommentId" value="${commentId}">
                        <input style="margin: auto" type="submit"
                               value="<fmt:message key="userInfoSave.name" bundle="${ rb }"/>">
                    </form>
                </form>
            </div>
        </div>
    </div>

</body>
</html>