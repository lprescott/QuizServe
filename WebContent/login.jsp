<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Login</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
	<div class="login-main">
		<div class="form-container">
    		<form class="login-form" action="LoginServlet" method="post">
      			<input id="email" name="email" type="text" placeholder="email"/>
      			<input id="password" name="password" type="password" placeholder="password"/>
      			<input id="submit" type="submit" value="LOGIN">
    		</form>
  		</div>
	</div>
</body>
</html>