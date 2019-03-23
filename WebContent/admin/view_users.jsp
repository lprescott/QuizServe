<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.io.*,java.util.*,java.sql.*"%>
<%@page import="javax.servlet.http.*,javax.servlet.*"%>
<%@page import="edu.albany.csi418.session.LoginEnum"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>

<head>
	<meta content="text/html;" charset="UTF-8">
	<title>View Users</title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
</head>

<body>
	<div class="header shadow">
		<a class="logo" href="${pageContext.request.contextPath}/login.jsp"><img class="shadow"
				style="max-height: 60px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg"
				alt="SUNY Albany Seal"></a>
		<div class="logo-label"></div>
		<p>Logged in as ${email}.</p>
		<form action="../Logout" method="post">
			<input type="submit" value="Logout?">
		</form>
	</div>

	<div class="main-container" style="max-width: 1000px;">
		<div class="main shadow">
			<!-- Content goes here. -->
			<sql:setDataSource var="snapshot" driver="com.mysql.cj.jdbc.Driver"
				url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
			<sql:query dataSource="${snapshot}" var="result"> SELECT * from USERS; </sql:query>
		
			<table class="table" style="width: 100%;">
				<tr>
					<th>ID</th>
					<th>Email</th>
					<th>Password</th>
					<th>Active</th>
					<th></th>
				</tr>
		
				<c:forEach var="row" items="${result.rows}">
					<tr>
						<td><c:out value="${row.USERS_ID}" /></td>
						<td><c:out value="${row.EMAIL}" /></td>
						<td><c:out value="${row.PASSWORD}" /></td>
						<td><c:out value="${row.IS_ACTIVE}" /></td>
						<td>
							<a class="link-style" href="${pageContext.request.contextPath}/admin/edit_user.jsp?id=<c:out value="${row.USERS_ID}"/>">edit</a>
						</td>
					</tr>
				</c:forEach>
				
				<tr>
					<td></td>
					<td>
						<a class="link-style" href="${pageContext.request.contextPath}/admin/create_user.jsp?id=<c:out value="${row.USERS_ID}"/>">create new user</a>
					</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</div>
	</div>

	<div class="footer shadow">
		<p>A quiz application for the ICSI 418Y final project, Spring
			2019.</p>
	</div>
</body>

</html>