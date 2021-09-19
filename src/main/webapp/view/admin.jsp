<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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
        <th>Barithday</th>
        <th>Email</th>
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
            <c:out value="${user.password}"></c:out>
        </td>
        <td>
            <c:out value="${user.privilege}"></c:out>
        </td>
        <td>
            <a href="UserController?action=edit&userId=<c:out value="${user.id}"/> Update</a>
            <a href="UserController?action=delete&userId=<c:out value="${user.id}"/> Delete</a>

        </td>
    </tr>
    </c:forEach>


<table border="1">
<h1>
    Instructors:
</h1>
    <tr>
        <th>Instructor Name</th>
        <th>Instructor ID</th>
        <th>Email</th>
        <th>Department</th>
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
            <c:out value="${user.password}"></c:out>
        </td>
        <td>
            <c:out value="${user.privilege}"></c:out>
        </td>
        <td>
            <a href="UserController?action=edit&userId=<c:out value="${user.id}"/> Update</a>
            <a href="UserController?action=delete&userId=<c:out value="${user.id}"/> Delete</a>

        </td>
    </tr>
    </c:forEach>

</form>

<table border="1">
<h1>
    Courses:
</h1>
    <tr>
        <th>Course Name</th>
        <th>Course ID</th>
        <th>Hours</th>
        <th>Grade</th>
        <th>Add to Course</th>
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
            <c:out value="${user.password}"></c:out>
        </td>
        <td>
            <c:out value="${user.privilege}"></c:out>
        </td>
        <td>
            <a href="UserController?action=delete&userId=<c:out value="${user.id}"/> Add Stuednt</a>
        </td>
	<td>
            <a href="UserController?action=edit&userId=<c:out value="${user.id}"/> Update</a>
            <a href="UserController?action=delete&userId=<c:out value="${user.id}"/> Delete</a>
        </td>
    </tr>
    </c:forEach>


</form>
</body>
</html>

