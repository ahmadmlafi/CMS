<html>
<head>
    <title>Courses Management System</title>
<h1>Welcome To Courses Management System</h1>
</head>
<body>
   <h1>Login</h1>
   <form name='f' action="login" method='POST'>
	<input id="tab-1" type="radio" name="tab" class="sign-in" checked><label for="tab-1" class="tab">Student</label>
        <input id="tab-2" type="radio" name="tab"><label for="tab-2" class="tab">Instructor</label>
	<input id="tab-3" type="radio" name="tab" ><label for="tab-1" class="tab">Admin</label>
            
      <table>
         <tr>
            <td>User:</td>
            <td><input type='text' name='username' value=''></td>
         </tr>
         <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
         </tr>
         <tr>
            <td><input name="submit" type="submit" value="Login" /></td>
         </tr>
      </table>
  </form>
</body>
</html>