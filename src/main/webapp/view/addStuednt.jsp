<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add student</title>
<form action="/logout">
    <input type="submit" value="Logout">
</head>
<body>
<%
    response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
    if (session.getAttribute("username") == null) {
        response.sendRedirect("/welcome.jsp");
    }
%>
<c:out value="${message}"></c:out>
<h1>Add Student</h1>
<form action="/addUser" method="post">
    Name : <input type=" text" name="name" placeholder="Full Name" required> <br>
    Email : <input type="email" name="email" placeholder="name@gmail.com" required> <br>
    Password : <input type="password" name="password" placeholder="Password" required> <br>
    DOB : <input type=" text" name="dob" placeholder="dd-mm-yyyy" required> <br>
    Major : <input type=" text" name="major" placeholder="Student Major" required> <br>
    <br>
    <br>
    <input type="submit" value="ADD">
</form>
<br>

</form>
</body>
</html>