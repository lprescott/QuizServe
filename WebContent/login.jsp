<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.util.*,java.sql.*"%>
<%@page import="javax.servlet.http.*,javax.servlet.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>

<head>
<meta content="text/html;" charset="UTF-8">
<title>Login</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
</head>

<body>
	<!-- Navbar -->
	<div class="header shadow">
		<a class="logo" href="${pageContext.request.contextPath}/admin/main.jsp"><img class="shadow" style="max-height: 65px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg" alt="SUNY Albany Seal"></a>
		<p style="float: left;">University at Albany, SUNY</p>
		<p>Not logged in.</p>
		<form action="Logout" method="post">
			<input style="text-decoration: none !important;" type="submit" value="Clear Session">
		</form>
	</div>

	<!--  Login -->
	<div class="login-main">
		<div class="form-container shadow">
			<!-- Form -->
			<form class="quiz-form" action="${pageContext.request.contextPath}/Login" method="post">
				<input id="email" name="email" type="email" placeholder="email" required> <input id="password" name="password" type="password" placeholder="password" required> <input id="submit" type="submit" value="LOGIN">
			</form>

			<!-- Error Message (if set) -->
			<%
				if (request.getParameter("success") != null) {
					if (request.getParameter("success").equals("false")) {
						out.println("<div style=\" margin-bottom: -45px;\" id=\"error\"><p>" + request.getParameter("error") + "</p></div>");
					}
				}
			%>
		</div>
	</div>

	<!-- Footer -->
	<div class="footer shadow">
		<p>
			A quiz application by <a class="link-style" href="${pageContext.request.contextPath}/about_us.jsp">our team</a> for an ICSI 418Y/410 final project, Spring 2019.
		</p>
	</div>
</body>

</html>