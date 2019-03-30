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
<title>Edit A Question</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/question.css">
<script src="${pageContext.request.contextPath}/js/checkBox.js"></script>
</head>

<body>
	<!-- Navbar -->
	<div class="header shadow">
		<a class="logo" href="${pageContext.request.contextPath}/admin/main.jsp"><img class="shadow" style="max-height: 60px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg" alt="SUNY Albany Seal"></a>
		<p style="float: left;">University at Albany, SUNY</p>
		<p>Logged in as ${email}.</p>		
		<a id="link" href="${pageContext.request.contextPath}/admin/question/question_management.jsp"> Go back </a>
		<form action="${pageContext.request.contextPath}/Logout" method="post">
			<input type="submit" value="Logout?">
		</form>
	</div>

	<!-- Content -->
	<div class="main-container">
		<div class="main shadow">
			<div class="form-container-question">
				<!-- Form -->

				<sql:setDataSource var="snapshot" driver="com.mysql.cj.jdbc.Driver" url="<%=LoginEnum.hostname.getValue()%>" user="<%=LoginEnum.username.getValue()%>" password="<%=LoginEnum.password.getValue()%>" />
				<sql:query dataSource="${snapshot}" var="result"> SELECT * FROM QUESTION WHERE QUESTION_ID=<%=request.getParameter("QUESTION_ID")%>; </sql:query>

				<form class="login-form" action="${pageContext.request.contextPath}/EditQuestionTF" method="post">

					<!-- Hidden input with ID# -->
					<input id="QUESTION_ID" type="hidden" name="QUESTION_ID" value="<%=request.getParameter("QUESTION_ID")%>">

					<textarea class="q_input_text" id="q_text" name="q_text" rows="10" cols="30" placeholder="Question text goes here..." required>${result.rows[0].TEXT}</textarea>
					<input class="q_input_text" id="q_category" name="q_category" type="text" placeholder="Category" value="${result.rows[0].CATEGORY}">
					<div class="padded-bottom">

						<c:if test="${result.rows[0].TF_IS_TRUE}">
						Answer: <input class="cb" type="checkbox" id="true_cb" name="true_cb" onchange="checkBoxUpdate(this)" checked>
							<label for="true">True</label>
							<input class="cb" type="checkbox" id="false_cb" name="false_cb" onchange="checkBoxUpdate(this)">
							<label for="false">False</label>
						</c:if>

						<c:if test="${!result.rows[0].TF_IS_TRUE}">
						Answer: <input class="cb" type="checkbox" id="true_cb" name="true_cb" onchange="checkBoxUpdate(this)">
							<label for="true">True</label>
							<input class="cb" type="checkbox" id="false_cb" name="false_cb" onchange="checkBoxUpdate(this)" checked>
							<label for="false">False</label>
						</c:if>
						
					</div>
					
					<div class="padded-bottom">
						Attached image: <input type="file" id="q_image" name="q_image" accept="image/png, image/jpeg"> <br>
					</div>

					<div class="padded-bottom">
						<input class="shadow-button" id="submit" type="submit" name="submit" value="UPDATE">
					</div>

					<input class="shadow-button" id="delete" type="submit" name="submit" value="DELETE">
				</form>

				<!-- Error Message (if set) -->
				<%
					if (request.getParameter("success") != null) {
						if (request.getParameter("success").equals("false")) {
							out.println("<div id=\"error\"><p>" + request.getParameter("error") + "</p></div>");
						}
					}
				%>

				<!-- Success Message (if set) -->
				<%
					if (request.getParameter("success") != null) {
						if (request.getParameter("success").equals("true")) {
							out.println("<div id=\"success\"><p>Successfully Updated Question</p></div>");
						}
					}
				%>
			</div>
		</div>
	</div>

	<!-- Footer -->
	<div class="footer shadow">
		<p>A quiz application for the ICSI 418Y final project, Spring 2019.</p>
	</div>
</body>

</html>