<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 02.03.2021
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" language="java"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custag" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>PoetryLike</title>
</head>
<body>

<c:url value="/index.html" var="indexUrl"/>
<form action="${indexUrl}" method="post">
    <%--        <jsp:forward page="/WEB-INF/jsp/views/menu/home.jsp"/> --%>
    <jsp:forward page="/WEB-INF/jsp/views/login.jsp"/>
</form>

</body>
</html>