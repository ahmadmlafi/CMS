<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>Courses</title>
<form action="/logout">
    <input type="submit" value="Logout">
</head>
<body>
<table border="1">
<h1>
    Students
</h1>
    <tr>
        <th>Student Name</th>
        <th>Student ID</th>
        <th>Email</th>
	<th>Grade</th>
        <th>Action</th>
    </tr>

    <tr>
        <c:forEach var="user" items="${users}">
        <td>
            <c:out value="${user.username}"></c:out>
        </td>
        <td>
            <c:out value="${user.email}"></c:out>
        </td>
        <td>
            <c:out value="${user.privilege}"></c:out>
        </td>
        <td>
            <c:out value="${user.privilege}"></c:out>
        </td>
        <td>
            <a href="UserController?action=edit&userId=<c:out value="${user.id}"/> Edit Grade </a>
        </td>
    </tr>
    </c:forEach>
</form>
</body>
</html>

