<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>

<%@page import="java.io.*,java.util.*,java.sql.*"%>
<%@page import="javax.servlet.http.*,javax.servlet.*"%>
<%@page import="edu.albany.csi418.session.LoginEnum"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>

<head>
<meta content="text/html;" charset="UTF-8">
<title>Update Account</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
</head>

<body>
	<!-- Navbar -->
	<div class="header shadow">
		<a class="logo" href="${pageContext.request.contextPath}/user/main.jsp"><img class="shadow" style="max-height: 65px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg" alt="SUNY Albany Seal"></a>
		<p style="float: left;">University at Albany, SUNY</p>
		<p>Logged in as ${email}.</p>
		<a id="link" href="${pageContext.request.contextPath}/user/main.jsp"> Go back </a>
		<form action="${pageContext.request.contextPath}/Logout" method="post">
			<input type="submit" value="Logout">
		</form>
	</div>

	<!-- Content -->
	<div class="login-main">
		<div class="form-container shadow">

			<!-- Error Message (if set) -->
			<%
				if (request.getParameter("success") != null) {
					if (request.getParameter("success").equals("false")) {
						out.println("<div style=\"text-align:center; padding-bottom:15px; margin: 5px;\" id=\"error\"><p>" + request.getParameter("error") + "</p></div>");
					}
				}
			%>

			<!-- Success Message (if set) -->
			<%
				if (request.getParameter("success") != null) {
					if (request.getParameter("success").equals("true")) {
						out.println("<div id=\"success\" style=\"text-align:center; padding-bottom:15px; margin: 5px;\"><p>User Successfully Updated</p></div>");
					}
				}
			%>

			<sql:setDataSource var="snapshot" driver="com.mysql.cj.jdbc.Driver" url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
			<sql:query dataSource="${snapshot}" var="result"> SELECT * FROM USERS WHERE USERS_ID=<%=session.getAttribute("id")%>; </sql:query>

			<form class="quiz-form" action="${pageContext.request.contextPath}/UpdateUser" method="post">

				<!-- Hidden input with ID# -->
				<input id="USERS_ID" type="hidden" name="USERS_ID" value="<%=session.getAttribute("id")%>"> <input id="email" name="email" type="email" placeholder="email" value="${result.rows[0].EMAIL}" required> <input id="password" name="password" type="password" placeholder="password" value="${result.rows[0].PASSWORD}" required> <input id="password-confirm" name="password-confirm" type="password" placeholder="confirm password" value="${result.rows[0].PASSWORD}" required>

				<div class="padded-bottom">
					<input class="shadow-button" id="submit" type="submit" name="submit" value="UPDATE">
				</div>

				<input class="shadow-button" id="delete" type="submit" name="submit" onclick="return confirm('Are you sure?');" value="DELETE">

			</form>
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