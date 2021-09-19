<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>edit student</title>
<form action="/logout">
    <input type="submit" value="Logout">
</head>
<body>
<c:out value="${message}"></c:out>
<h1>Edit Student</h1>
<form action="/addUser" method="post">
    Name : <input type=" text" name="name" placeholder="Full Name" required> <br>
    Email : <input type="email" name="email" placeholder="name@gmail.com" required> <br>
    Password : <input type="password" name="password" placeholder="Password" required> <br>
    DOB : <input type=" text" name="dob" placeholder="dd-mm-yyyy" required> <br>
    Major : <input type=" text" name="major" placeholder="Student Major" required> <br>
    <br>
    <br>
    <input type="submit" value="SAVE">
</form>
<br>

</form>
</body>
</html>