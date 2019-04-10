<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.io.*,java.util.*,java.sql.*"%>
<%@page import="javax.servlet.http.*,javax.servlet.*"%>
<%@page import="edu.albany.csi418.session.LoginEnum"%>
<%@page import="edu.albany.csi418.DateTimeUtils"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>

<head>
<meta content="text/html;" charset="UTF-8">
<title>Test A Test</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">

</head>

<body>
	<sql:setDataSource var="snapshot1" driver="com.mysql.cj.jdbc.Driver" url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
	<sql:query dataSource="${snapshot1}" var="result1"> SELECT * FROM TEST T INNER JOIN ADMINISTRATOR A ON T.ADMIN_ID = A.ADMIN_ID WHERE TEST_ID = <%=request.getParameter("TEST_ID")%>; </sql:query>

	<!-- Navbar -->
	<div class="header shadow">
		<a class="logo" href="${pageContext.request.contextPath}/user/main.jsp"><img class="shadow" style="max-height: 60px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg" alt="SUNY Albany Seal"></a>
		<p style="float: left;">${result1.rows[0].HEADER_TEXT}</p>
		<p>Logged in as ${email}.</p>
		<a id="link" href="${pageContext.request.contextPath}/user/main.jsp"> Go back </a>
		<form action="${pageContext.request.contextPath}/Logout" method="post">
			<input type="submit" value="Logout">
		</form>
	</div>

	<!-- Content -->
	<div class="main-container">
		<div class="main shadow">
			<h2 style="margin: 10px;">${result1.rows[0].TITLE}</h2>
			<h3 style="margin: 10px;">Administered by ${result1.rows[0].EMAIL}</h3>
		</div>
	</div>

	<sql:query dataSource="${snapshot1}" var="result2"> SELECT * FROM QUESTION Q INNER JOIN TEST_QUESTIONS TQ ON Q.QUESTION_ID = TQ.QUESTION_ID WHERE TQ.TEST_ID = <%=request.getParameter("TEST_ID")%>; </sql:query>

	<form class="login-form" action="${pageContext.request.contextPath}/SubmitTest" method="post">


		<c:forEach var="row" items="${result2.rows}">
			<div class="main-container">
				<div class="main shadow">${row.TEXT}</div>
			</div>
		</c:forEach>

		<div class="main-container">
			<div class="main shadow">
				<input style="text-transform: uppercase; outline: 0; background: #93cede; width: 100%; border: 0; padding: 15px; color: #ffffff; font-size: 14px; -webkit-transition: all 0.3 ease; transition: all 0.3 ease; cursor: pointer;" class="shadow-button" id="submit" type="submit" value="submit">
			</div>
		</div>

	</form>



	<!-- Footer -->
	<div class="footer shadow">
		<p>${result1.rows[0].FOOTER_TEXT}</p>
	</div>
</body>

</html>