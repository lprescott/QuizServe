<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.io.*,java.util.*,java.sql.*"%>
<%@page import="javax.servlet.http.*,javax.servlet.*"%>
<%@page import="edu.albany.csi418.session.LoginEnum"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
<head>
<meta content="text/html;" charset="UTF-8">
<title>Test List</title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>
<!-- Navbar -->
	<div class="header shadow">
		<a class="logo" href="${pageContext.request.contextPath}/admin/main.jsp"><img class="shadow"
				style="max-height: 60px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg"
				alt="SUNY Albany Seal"></a>
		<div class="logo-label"></div>
		<p style="float: left;">University at Albany, SUNY</p>
		<p>Logged in as ${email}.</p>		<form action="${pageContext.request.contextPath}/Logout" method="post">
			<input type="submit" value="Logout?">
		</form>
	</div>
	
	<!-- Content -->
	<div class="main-container" style="max-width: 1000px;">
		<div class="main shadow" style="padding: 0;">

			<!-- Connect to DB and select all users -->
			<sql:setDataSource var="snapshot" driver="com.mysql.cj.jdbc.Driver" url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
			<sql:query dataSource="${snapshot}" var="result"> SELECT TEST_ID, HEADER_TEXT from TEST; </sql:query>

			<!-- Print table of all users -->
			<table class="table" style="width: 100%;">
				<tr>
					<th>ID</th>
					<th>Test Name</th>
				</tr>

				<c:forEach var="row" items="${result.rows}">
					<tr>
						<td><c:out value="${row.TEST_ID}" /></td>
						<td><c:out value="${row.HEADER_TEXT}" /></td>
						<td><a class="link-style" href="${pageContext.request.contextPath}/admin/test/edit_test.jsp?TEST_ID=<c:out value="${row.TEST_ID}"/>">edit</a></td>
					</tr>
				</c:forEach>

				<tr>
					<td></td>
					<td><a class="link-style" href="${pageContext.request.contextPath}/admin/test/create_test.jsp">create new test</a></td>
				</tr>
			</table>

			<!-- Message (if set) -->
			<%
				if (request.getParameter("message") != null) {
					out.println("<div style=\"padding-bottom: 5px; text-align: center;\" id=\"success\"><p>"
							+ request.getParameter("message") + "</p></div>");
				}
			%>

		</div>
	</div>

	<!-- Footer -->
	<div class="footer shadow">
		<p>A quiz application for the ICSI 418Y final project, Spring 2019.</p>
	</div>
</body>

</html>