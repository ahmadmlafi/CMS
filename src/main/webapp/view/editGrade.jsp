<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>edit grade</title>
</head>
<body>
   <h1>Edit Grade</h1>
   <form name='f' action="editGrade" method='POST'>
    Grade : <input type=" text" name="grade" placeholder="New Grade" required> <br>
    <input name="submit" type="submit" value="SAVE" />
  </form>

</body>
</html>