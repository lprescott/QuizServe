<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.util.*,java.sql.*"%>
<%@page import="javax.servlet.http.*,javax.servlet.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>

<head>
	<meta content="text/html;" charset="UTF-8">
	<title>Create A Question</title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/question.css">
</head>

<body>
	<!-- Navbar -->
	<div class="header shadow">
		<a class="logo" href="${pageContext.request.contextPath}/admin/main.jsp"><img class="shadow"
				style="max-height: 60px;" src="${pageContext.request.contextPath}/img/graphic-seal.jpg"
				alt="SUNY Albany Seal"></a>
		<p style="float: left;">University at Albany, SUNY</p>
		<p>Logged in as ${email}.</p>		
		<a id="link" href="${pageContext.request.contextPath}/admin/question/question_management.jsp">
			Go back </a>
		<form action="${pageContext.request.contextPath}/Logout" method="post">
			<input type="submit" value="Logout">
		</form>
	</div>

	<!-- Content -->
	<div class="main-container">
		<div class="main shadow">
			<div class="form-container-question">
				<!-- Form -->
				<form class="login-form" action="${pageContext.request.contextPath}/CreateQuestionMC" method="post">
					<textarea class="q_input_text" id="q_text" name="q_text" rows="10" cols="30"
						placeholder="Question text goes here..." required></textarea>
					<input class="q_input_text" id="q_category" name="q_category" type="text" placeholder="Category">
					<input class="q_input_text" id="q_a1" name="q_a1" type="text" placeholder="Answer #1" required>
					<input class="q_input_text" id="q_a2" name="q_a2" type="text" placeholder="Answer #2" required>
					<input class="q_input_text" id="q_a3" name="q_a3" type="text" placeholder="Answer #3" required>
					<input class="q_input_text" id="q_a4" name="q_a4" type="text" placeholder="Answer #4" required>
					<input class="q_input_text" id="q_a5" name="q_a5" type="text" placeholder="Answer #5"> <input
						class="q_input_text" id="q_a6" name="q_a6" type="text" placeholder="Answer #6">
					<div class="padded-bottom">
						Correct Answer #: <input id="q_correct_answer" type="number" name="q_correct_answer" min="1"
							max="6" required>
					</div>
					<div class="padded-bottom">
						Attached image: <input type="file" id="q_image" name="q_image" accept="image/png, image/jpeg">
					</div>
					<input class="shadow-button" id="submit" type="submit" value="CREATE QUESTION">
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
							out.println("<div id=\"success\"><p>Successfully Added Question</p></div>");
						}
					}
				%>
			</div>
		</div>
	</div>

	<!-- Footer -->
	<div class="footer shadow">
		<p>A quiz application for the ICSI 418Y final project, Spring
			2019.</p>
	</div>
</body>

</html>