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
<title>Test A Test</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<script src="${pageContext.request.contextPath}/js/checkBox.js"></script>

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

	<sql:query dataSource="${snapshot1}" var="result2"> SELECT * FROM TEST T INNER JOIN TEST_QUESTIONS TQ ON T.TEST_ID = TQ.TEST_ID INNER JOIN QUESTION Q ON TQ.QUESTION_ID = Q.QUESTION_ID WHERE T.TEST_ID = <%=request.getParameter("TEST_ID")%>; </sql:query>

	<form class="login-form" action="${pageContext.request.contextPath}/SubmitTest" method="post">
		<div class="main-container">
			<div class="main shadow">

				<c:set var="count" value="1" scope="page" />
				<c:forEach var="row" items="${result2.rows}">

					<h4>${count}. ${row.TEXT}</h4>
					<!-- question is mc -->
					<c:if test="${row.IS_TRUE_FALSE == false}">
						<sql:query dataSource="${snapshot1}" var="result3"> SELECT * FROM QUESTION Q INNER JOIN QUESTION_ANSWER QA ON Q.QUESTION_ID = QA.QUESTION_ID INNER JOIN ANSWER A ON QA.ANSWER_ID = A.ANSWER_ID WHERE Q.QUESTION_ID = ${row.QUESTION_ID};</sql:query>
						<c:forEach var="row2" items="${result3.rows}">
							<input class="${row.QUESTION_ID}" type="checkbox" id="${row2.ANSWER_ID}" name="${row2.ANSWER_ID}" onchange="checkBoxUpdate(this, '${row2.QUESTION_ID}')">
							<label for="${row2.ANSWER_ID}">${row2.ANSWER}</label>
							<br>
						</c:forEach>
						<br>
						<hr style="margin-left: -20px; margin-right: -20px;">

					</c:if>
					<!-- question is t/f -->
					<c:if test="${row.IS_TRUE_FALSE == true}">
						<input class="${row.QUESTION_ID}" type="checkbox" id="${row.QUESTION_ID}_true" name="${row.QUESTION_ID}_true" onchange="checkBoxUpdate(this, '${row.QUESTION_ID}')">
						<label for="${row.QUESTION_ID}_true">True</label>
						<br>
						<input class="${row.QUESTION_ID}" type="checkbox" id="${row.QUESTION_ID}_false" name="${row.QUESTION_ID}_false" onchange="checkBoxUpdate(this, '${row.QUESTION_ID}')">
						<label for="${row.QUESTION_ID}_false">False</label>
						<br>
						<br>
						<hr style="margin-left: -20px; margin-right: -20px;">
					</c:if>

					<c:set var="count" value="${count + 1}" scope="page" />
				</c:forEach>
				<div style="padding-top: 30px">
					<input onClick="return validateCheckboxes();" class="shadow-button submit-button" id="submit" type="submit" value="submit">
				</div>
			</div>
		</div>
	</form>



	<!-- Footer -->
	<div class="footer shadow">
		<p>${result1.rows[0].FOOTER_TEXT}</p>
	</div>
</body>

</html>