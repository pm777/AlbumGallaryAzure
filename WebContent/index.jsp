<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login page</title>
<style>
div {
    width: 320px;
    padding: 10px;
    border: 5px solid gray;
    margin: 0;
}
</style>
</head>
<body>
<center>
<form action="LoginServlet">
<h2>Please Enter your name :</h2>
<div>
<input type="text" name="username" placeholder="Enter Username"><br><br><br>
<input type=submit value="Login">
</div>
</form>
</center>
</body>
</html>