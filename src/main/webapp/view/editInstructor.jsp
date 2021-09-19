<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>edit instructor</title>
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
<h1>Edit Instructor</h1>
<form action="/addUser" method="post">
    Name : <input type=" text" name="name" placeholder="Full Name" required> <br>
    Email : <input type="email" name="email" placeholder="name@gmail.com" required> <br>
    Password : <input type="password" name="password" placeholder="Password" required> <br>
    Phone No. : <input type=" text" name="phone" placeholder="07xxxxxxxx" required> <br>
    department : <input type=" text" name="department" placeholder="Instructor Department" required> <br>
    <br>
    <br>
    <input type="submit" value="SAVE">
</form>
<br>

</form>
</body>
</html>